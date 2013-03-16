package newlaw.bpm.phase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import newlaw.bpm.BpmException;
import newlaw.bpm.ProcessData;
import newlaw.bpm.attachment.AttachmentService;
import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.processinstance.ProcessInstanceService;
import newlaw.bpm.search.TooManySearchResultsException;
import newlaw.bpm.transition.Transition;
import newlaw.bpm.transition.TransitionService;
import newlaw.domain.DevData;
import newlaw.identities.LdapService;
import newlaw.identities.UserDetailsService;
import newlaw.util.AppUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.util.WebUtils;

// TODO: Auto-generated Javadoc
/**
 * Controller for running process workflows.
 */
@Controller
@SessionAttributes("command")
public class PhaseController implements ServletContextAware {

	@Autowired
	PhaseService phaseService;
	
	@Autowired
	TransitionService transitionService;
	
	@Autowired
	AttachmentService attachmentService;
	
	@Autowired
	UserDetailsService userService;

	@Autowired
	LdapService ldapService;

	protected Log log = LogFactory.getLog(this.getClass());

	/** The context. */
	ServletContext context; // this is used to get local resource files (task
							// forms)


	/**
							 * Display the phase view for a given process instance. It checks to see if an associated jspx page exists, if so, displays it. 
							 * If the phase is an automated phase it attempts to run it. This will only occur if the automated phase failed when the .POST method was called.
							 *
							 * @param id the id
							 * @param phase the phase
							 * @param page the page
							 * @param sub the sub
							 * @param modelMap the model map
							 * @param session the session
							 * @return the string
							 * @throws BpmException the bpm exception
							 */
							@RequestMapping(value = "/{id}/{phase}", method = RequestMethod.GET)
	public String phase(@PathVariable("id") Long id, @PathVariable("phase") String phase,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "sub", required = false) String sub, ModelMap modelMap, SessionStatus session)
			throws BpmException {

		PhaseCommand command = null;
		if (modelMap.containsKey("command")) {
			// Command object already set up use that one.
			command = (PhaseCommand) modelMap.get("command");
		} else {
			command = new PhaseCommand();			
		}
		
		command.setAddEntry("");

		modelMap.addAttribute("command", command);
		
		// Save and restore transient vars XXX a bit messy. Maybe transient vars should go in command?
		Hashtable<String,Object> tvs;
		if(command.getProcessInstance() != null) 
			tvs = command.getProcessInstance().getTransientVariables();
		else 
			tvs = new Hashtable<String,Object>();
		
		command.setProcessInstance(ProcessInstance.findProcessInstance(id));
		command.getProcessInstance().setTransientVariables(tvs);

		ProcessDefinition pd = command.getProcessInstance().getProcessDefinition();
		Phase phaseObj = pd.getPhase(phase);
		ProcessInstanceService service = pd.getService();

		//if(command.getReviewMode() != Boolean.TRUE) {
			// Check the phase is valid for the process instance.
			boolean validPhase = false;
			for (PhaseAssignment p : command.getProcessInstance().getPhaseAssignments()) {
				if (p.getPhaseCode().equals(phaseObj.getCode())) {
					validPhase = true;
					break;
				}
			}
		
			if (!validPhase) {
				if(command.getCurrentPhase() != null) {
					boolean rollbackSucceeded =phaseService.attemptRollback(command.getProcessInstance(), pd.getPhase(command.getCurrentPhase()), phaseObj);
					if(!rollbackSucceeded)
						command.setReviewMode(Boolean.TRUE);
				} else 
					command.setReviewMode(Boolean.TRUE);
			} else {
				command.setReviewMode(Boolean.FALSE);
			}
		//}
			// Check the user has rights to run this phase.
		phaseService.updatePhaseAssignmentRights(command.getProcessInstance());
		for (PhaseAssignment pa : command.getProcessInstance().getPhaseAssignments()) {
			if (pa.getPhaseCode().equals(phase) && !pa.getCurrentUserHasAuthority()) {
					BindingResult result = new BeanPropertyBindingResult(command, "command");
					result.addError(new ObjectError("command", "You do not have sufficient rights to run this phase."));
					modelMap.addAttribute("org.springframework.validation.BindingResult.command", result);
					session.setComplete();
					return "bpm/error";
			}
		}

		// Run the action handler for this phase
		// TODO: Are they running twice- one following the last post and then here?
		try {
			if(transitionService.isRunActionHandlers()) {
				List<Transition> tl = null;
				try {
					tl = transitionService.runActionHandler(phaseObj.getActionHandler(), command.getProcessInstance(), command.getMessages());
				} catch (Exception e) {
					BindingResult result = new BeanPropertyBindingResult(command, "command");
					result.addError(new ObjectError("command", e.getMessage()+": "+e.getCause().getMessage()));
					modelMap.addAttribute("org.springframework.validation.BindingResult.command", result);
					session.setComplete();
					return "bpm/error";
				}
				if(tl != null && tl.size() > 0) {
					for(Transition t : tl) {
						transitionService.trigger(command.getProcessInstance(), command.getMessages(), t.getCode(), t.getPhase().getCode());
					}
					
					PhaseAssignment pa = phaseService.usersNextPhaseAssignment(command.getProcessInstance());
					if (pa==null) {
						// Nothing for user to do - goto case
						session.setComplete();
						return "redirect:/pi/" + command.getProcessInstance().getId();
					} else 
						return "redirect:/" + command.getProcessInstance().getId() + "/" + pa.getPhaseCode();
				}
			}
		} catch (Exception e) {
			BindingResult result = new BeanPropertyBindingResult(command, "command");
			result.addError(new ObjectError("command", e.getMessage() == null ? "Null pointer" : e.getMessage()));
			modelMap.addAttribute("org.springframework.validation.BindingResult.command", result);
			session.setComplete();
			return "bpm/error";
		}

		// Ensure the task form is present
		String formPath = "/WEB-INF/views/bpm/taskForms/" + pd.getCode() + "/" + phase + ".jspx";
		try {
			this.context.getResource(formPath).openStream().close();
			
			command.setTaskForm(phaseObj.getCode());
		} catch (Exception e) {
			command.setTaskForm(null);
			if (phaseObj.getDefaultPage() != null)
				throw new BpmException("Task form missing: " + formPath);
		}

		// If its the first time here, set the default page for phase.
		if (page == null) {
			command.setCurrentPage(phaseObj.getDefaultPage());
		} else
			command.setCurrentPage(page);

		command.setCurrentSubPage(sub);
		command.setCurrentPhase(phase);

		if(command.getCurrentPage() != null) {
			// show the required fields. 
			WebDataBinder db = new WebDataBinder(command);
			this.binder(db);
			db.bind(new MutablePropertyValues(modelMap));
			BindingResult result = db.getBindingResult();
			service.validate(command.getProcessInstance(), command.getCurrentPhase(), command.getCurrentPage(),
					command.getCurrentSubPage(), result, true);
			modelMap.addAttribute("org.springframework.validation.BindingResult.command", result);
		}

		// Save the dev/test data
		DevData dd = DevData.findDevData(phase);
		if (dd != null) {
			command.setDevComments(dd);
		} else {
			dd = new DevData();
			dd.setKey_(phase);
			command.setDevComments(dd);
		}

		modelMap.addAttribute("phase", phaseObj);

		// Supply other phases within for the process instance in the model.
		// Probably only needed for testing.
		List<PhaseAssignment> userPhases = new ArrayList<PhaseAssignment>();
		for (PhaseAssignment pa : command.getProcessInstance().getPhaseAssignments())
			if (!pa.getPhaseCode().equals(phase))
				userPhases.add(pa);
		modelMap.addAttribute("otherPhases", userPhases);

		service.populateTaskFormModel(modelMap, command.getCurrentPhase(), command.getCurrentPage());
		
		modelMap.addAttribute("messages", command.getMessages());
		command.setMessages(new ArrayList<String>());
		
		return "bpm/default";
	}

	/**
	 * If user clicks the search button for a organisation or user it run this. The search string is validated and the results are returned.
	 *
	 * @param search the search
	 * @param phase the phase
	 * @param command the command
	 * @param result the result
	 * @param id the id
	 * @param modelMap the model map
	 * @return the string
	 * @throws BpmException the bpm exception
	 */
	@RequestMapping(value = "/{id}/{phase}", params = "search", method = RequestMethod.POST)
	public String search(@RequestParam("search") String search, 
			@PathVariable("phase") String phase,
			@ModelAttribute("command") PhaseCommand command, BindingResult result, @PathVariable("id") Long id,
			ModelMap modelMap) throws BpmException {
		ProcessInstanceService service = command.getProcessInstance().getProcessDefinition().getService();
		
		command.setSearchResults(null);

		String[] typeEntitySeq = search.split(",");
		if (typeEntitySeq.length < 3)
			throw new IllegalArgumentException("Search request missing type, entity or sequence; " + search);

		if(command.getSearchText() != null && command.getSearchText().equals("%create%")) {
			setupModel(modelMap, command, result);
			command.setAddEntry(command.getAddEntry()+typeEntitySeq[2]);
			return "bpm/default";
		}

		if (command.getSearchText() == null || command.getSearchText().trim().length() < 3) {
			result.rejectValue("searchText",null, "Search text too short.");
			setupModel(modelMap, command, result);
			return "bpm/default";
		}
		command.setSearchSeq(new Integer(typeEntitySeq[2]));
		String[] searches = command.getSearchText().split(",");			
		if(searches.length == 0)
			command.setSearchText("");
		else
			command.setSearchText(searches[command.getSearchSeq() - 1]);
		
		//TODO: what if jsr303 error?
		//service.validateAndSave(command.getProcessInstance(), command.getCurrentPhase(), command.getCurrentPage(),
				//command.getCurrentSubPage(), new BeanPropertyBindingResult(new Object(), "arse"));
		//service.save(command.getProcessInstance(), command.getCurrentPhase(), 
		//		command.getCurrentPage(),command.getCurrentSubPage());
		try {

			
			if (typeEntitySeq[0].equals("organisation"))
				command.setSearchResults(service.organisationSearch(typeEntitySeq[1],
						searches[command.getSearchSeq() - 1]));
			else if (typeEntitySeq[0].equals("user"))
				command.setSearchResults(ldapService.findUsersBySubstring(searches[command.getSearchSeq() - 1]));


			if (command.getSearchResults().size() == 0) {
				result.rejectValue("searchText", null,"Nothing found.");
				modelMap.addAttribute("nothingFound", typeEntitySeq[2]);
				setupModel(modelMap, command, result);
				return "bpm/default";
			}
		} catch (TooManySearchResultsException e) {
			result.rejectValue("searchText", null,"Too many search results, please refine search.");
			setupModel(modelMap, command, result);
			return "bpm/default";
		} catch (Exception e) {
			result.rejectValue("searchText", null,"Search failed: " + e.getMessage());
			setupModel(modelMap, command, result);
			return "bpm/default";

		}
		log.debug(search + " containing " + command.getSearchText());

		setupModel(modelMap, command, result);
		return "bpm/default";
	}

	/**
	 * Trigger a transition for a given phase.  This runs when the user selects a transition and clicks complete. The transition is follows to the next manual task running any automated tasks in between.
	 *
	 * @param page the page
	 * @param sub the sub
	 * @param phase the phase
	 * @param command the command
	 * @param result the result
	 * @param id the id
	 * @param request the request
	 * @param modelMap the model map
	 * @param session the session
	 * @return the string
	 */
	@RequestMapping(value = "/{id}/{phase}", method = RequestMethod.POST)
	public String trigger(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "sub", required = false) String sub, @PathVariable("phase") String phase,
			@ModelAttribute("command") PhaseCommand command, BindingResult result, @PathVariable("id") Long id,
			HttpServletRequest request, ModelMap modelMap, SessionStatus session) {

		ProcessInstanceService service = command.getProcessInstance().getProcessDefinition().getService();

		command.setDevComments(command.getDevComments().merge());

		// Clear search results so they dont transfer to the next form
		command.setSearchResults(null);
		command.setSearchText(null);
		command.getMessages().clear();

		if (command.getCurrentPage() != null) {
			if(command.getReviewMode() != Boolean.TRUE || userService.hasAuthority("NL24_UpdateForms"))
			// We're on a page so validate it
				service.validate(command.getProcessInstance(), command.getCurrentPhase(), command.getCurrentPage(),
						command.getCurrentSubPage(), result,false);

			if (result.hasErrors()) {
				setupModel(modelMap, command, result);
				return "bpm/default";
			} else {
				if(command.getReviewMode() != Boolean.TRUE || userService.hasAuthority("NL24_UpdateForms"))
					service.save(command.getProcessInstance(), command.getCurrentPhase(), command.getCurrentPage(),
						command.getCurrentSubPage());
				// command.setProcessInstance(command.getProcessInstance().merge());
				if (page != null) {
					// User has selected a page to go to
					command.setCurrentPage(page);
				}
				command.setCurrentSubPage(sub);
				// return "redirect:/" +
				// command.getProcessInstance().getId()+"/"+phase+"?page="+command.getCurrentPage();
			}
		}

		phase = command.getCurrentPhase();

		if (result.hasErrors()) {
			setupModel(modelMap, command, result);
			return "bpm/default";
		}

		// Trigger the task
		//String[] taskAndPhase = getTaskAndPhase(request);
		if (page == null && request.getParameterMap().containsKey("transition") 
				&& command.getReviewMode() != Boolean.TRUE ) {
			String transition= request.getParameter("transition");
			command.setProcessInstance(ProcessInstance.findProcessInstance(id));
			phaseService.validatePhase(command.getProcessInstance(), command.getCurrentPhase(), result);
			if (result.hasErrors()) {
				result.addError(new ObjectError("command", "One or more pages contain errors."));
				modelMap.addAttribute("org.springframework.validation.BindingResult.command", result);
				setupModel(modelMap, command, result);
				return "bpm/default";
			}
			
			try {
				command.getMessages().clear();
				command.setProcessInstance(command.getProcessInstance().merge());
				
				// Save last id for pa so we can know which ones were added as a result of trigger
				List<Long> ll=PhaseAssignment.entityManager().createQuery(
						"select max(id) from PhaseAssignment where processInstance=?", Long.class)
						.setParameter(1, command.getProcessInstance()).getResultList();
				Long lastId = 0L;
				if(ll.size() > 0)
					lastId = ll.get(0);

				
				command.setProcessInstance(transitionService.trigger(command.getProcessInstance(), command.getMessages(),
						transition, command.getCurrentPhase()));

				List<PhaseAssignment> newPhaseAssignments = PhaseAssignment.entityManager().createQuery(
						"from PhaseAssignment where processInstance=? and id > ?", PhaseAssignment.class)
						.setParameter(1, command.getProcessInstance()).setParameter(2, lastId).getResultList();
				Collections.sort(newPhaseAssignments);
				
				// Clear the current pages so that it will start on the
				// first page in subsequent forms
				command.setCurrentPage(null);
				command.setCurrentSubPage(null);
				phase = null;
				for(PhaseAssignment pa : newPhaseAssignments) {
					if(pa.getDue().compareTo(new Date()) <= 0 
							&& (phaseService.hasAssignedAuthorities(pa) ||
							phaseService.hasSwimlaneAuthorities(pa))) {
						phase = pa.getPhaseCode();
						break;
					}
							
				}
			} catch (BpmException e) {
				// Unable to recover since we could have triggered some automated phases.				
				result.addError(new ObjectError("command", e.getMessage()));
				modelMap.addAttribute("org.springframework.validation.BindingResult.command", result);
				return "bpm/error";
			}
		}
		
		//TODO: low - name and description
		attachmentService.saveAttachment(command.getProcessInstance(), command.getFileData(), command.getFileCode(), null, null);
		command.setFileData(null);

		if(phase == null || request.getParameterMap().containsKey("reviewComplete")) {
			session.setComplete();
			return "redirect:/pi/" + command.getProcessInstance().getId();
		} 

		String p;
		if (command.getCurrentPage() == null)
			p = "";
		else {
			p = "?page=" + command.getCurrentPage();
			if (command.getCurrentSubPage() != null)
				p += "&sub=" + command.getCurrentSubPage();
		}
		return "redirect:/" + command.getProcessInstance().getId() + "/" + phase + p;
	}

	/**
	 * Setup model.
	 *
	 * @param modelMap the model map
	 * @param command the command
	 * @param result the result
	 * @throws BpmException the bpm exception
	 */
	protected void setupModel(ModelMap modelMap, PhaseCommand command, BindingResult result) throws BpmException {
		modelMap.addAttribute("org.springframework.validation.BindingResult.command", result);
		modelMap.addAttribute("command", command);
		
		List<Transition> taskTypes = new ArrayList<Transition>();
		Hashtable<String, ProcessDefinition> defs = (Hashtable<String, ProcessDefinition>) AppUtils
				.getApplicationContext().getBean("processDefinitions");
		for (ProcessDefinition pd : defs.values()) {
			for (Phase p : pd.getPhases())
				for (Transition t : p.getTransitions())
					taskTypes.add(t);
		}
		modelMap.addAttribute("taskTypes", taskTypes);
		modelMap.addAttribute("phase",
				command.getProcessInstance().getProcessDefinition().getPhase(command.getCurrentPhase()));

		ProcessDefinition pd = command.getProcessInstance().getProcessDefinition();
		ProcessInstanceService service = pd.getService();
		service.populateTaskFormModel(modelMap, command.getCurrentPhase(), command.getCurrentPage());
	}

	/**
	 * Gets the task and phase.
	 *
	 * @param request the request
	 * @return the task and phase
	 */
	private String[] getTaskAndPhase(HttpServletRequest request) {
		//XXX: could have mapped in to the command or could have set the task/phase as the value used request.getParameter("task")
		Enumeration<String> e = request.getParameterNames();
		String[] tp = new String[2];
		while (e.hasMoreElements()) {
			String n = e.nextElement();
			if (n.startsWith("task=")) {
				tp = n.replaceAll("^task=", "").replaceAll("phase=", "").split(",");
				return tp;

			}
		}
		return null;

	}

	/**
	 * Create a new process. The dash has "start claim" button which sets the key variable below to "fnol1" 
	 *
	 * @param key the key
	 * @param request the request
	 * @param modelMap the model map
	 * @param session the session
	 * @return the string
	 * @throws BpmException the bpm exception
	 */
	@RequestMapping(value = "/create/{key}", method = RequestMethod.POST)
	public String create(@PathVariable("key") String key, HttpServletRequest request, ModelMap modelMap,
			SessionStatus session) throws BpmException {
		Hashtable defs = (Hashtable) AppUtils.getApplicationContext().getBean("processDefinitions");
		ProcessDefinition pd = (ProcessDefinition) defs.get(key);

		PhaseCommand command = new PhaseCommand();
		ProcessInstance c = pd.getService().startProcessInstance(key);
		command.setProcessInstance(c);

		DevData dd = DevData.findDevData("start");
		if (dd != null) {
			command.setDevComments(dd);
		} else {
			dd = new DevData();
			dd.setKey_("start");
			command.setDevComments(dd);
		}

		modelMap.addAttribute("command", command);
		return "redirect:/"+c.getId()+"/start";
		//return this.trigger(null, null, "start", command, new BeanPropertyBindingResult(command, "command"), c.getId(),
				//request, modelMap, session);
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;

	}


	/**
	 * Binder.
	 *
	 * @param binder the binder
	 */
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

		// TODO: low - this shouldn't be here
		binder.registerCustomEditor(Date.class, "processInstance.data.accident.accidentTime", new CustomDateEditor(
				new SimpleDateFormat("HH:mm"), true));

		// TODO: processService.initBinder(binder);
	}

	/**
	 * Adds the.
	 *
	 * @param add the add
	 * @param phase the phase
	 * @param command the command
	 * @param result the result
	 * @param id the id
	 * @param modelMap the model map
	 * @param request the request
	 * @param session the session
	 * @return the string
	 * @throws BpmException the bpm exception
	 */
	@RequestMapping(value = "/{id}/{phase}", params = "add", method = RequestMethod.POST)
	public String add(@RequestParam("add") String add, @PathVariable("phase") String phase,
			@ModelAttribute("command") PhaseCommand command, BindingResult result, @PathVariable("id") Long id,
			ModelMap modelMap, HttpServletRequest request, SessionStatus session) throws BpmException {
		ProcessInstanceService service = command.getProcessInstance().getProcessDefinition().getService();

		/*
		 * PhaseCommand command; if(!modelMap.containsAttribute("command")) {
		 * // Session has expired or something. //TODO: low - maybe data lost -
		 * what should we do? return this.processInstance(id, phase, null, null,
		 * modelMap, session); } else { command = (PhaseCommand)
		 * modelMap.get("command"); }
		 * 
		 * BindingResult result = bind(command,request);
		 */
		service.validate(command.getProcessInstance(), command.getCurrentPhase(), command.getCurrentPage(),
				command.getCurrentSubPage(), result,false);

		if (result.hasErrors()) {
			setupModel(modelMap, command, result);
			return "bpm/default";
		} else {
			service.save(command.getProcessInstance(), command.getCurrentPhase(), command.getCurrentPage(),
					command.getCurrentSubPage());
		}
		command.setCurrentSubPage(service.add(command.getProcessInstance(), add).toString());

		return "redirect:/" + command.getProcessInstance().getId() + "/" + phase + "?page=" + command.getCurrentPage()
				+ "&sub=" + command.getCurrentSubPage();
	}

	/**
	 * Process instance.
	 *
	 * @param id the id
	 * @param modelMap the model map
	 * @return the string
	 * @throws BpmException the bpm exception
	 */
	@RequestMapping(value = "/{id}/xml", method = RequestMethod.GET)
	public String processInstance(@PathVariable("id") Long id, ModelMap modelMap) throws BpmException {
		ProcessData data = ProcessInstance.findProcessInstance(id).getData();
		modelMap.addAttribute("data", data);
		return "processInstanceXml";
	}

}
