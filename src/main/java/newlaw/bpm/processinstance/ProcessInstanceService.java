package newlaw.bpm.processinstance;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import newlaw.bpm.BpmException;
import newlaw.bpm.attachment.Attachment;
import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.phase.PhaseAssignment;
import newlaw.bpm.phase.PhaseService;
import newlaw.bpm.search.TooManySearchResultsException;
import newlaw.bpm.swimlane.Swimlane;
import newlaw.bpm.swimlane.SwimlaneAssignment;
import newlaw.bpm.swimlane.SwimlaneService;
import newlaw.domain.Organisation;
import newlaw.identities.UserDetailsService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
/*
 * Service that relates to a particular process definition. It could be different
 * for different version of a process def. A reference is stored in the process def.
 */
@RooJavaBean
public abstract class ProcessInstanceService {

	protected static final Log LOG = LogFactory.getLog(ProcessInstanceService.class);

	@PersistenceContext
	EntityManager em;

	// @Autowired
	transient MailSender mailTemplate;

	@Autowired
	UserDetailsService userService;

	@Autowired
	PhaseService phaseService;

	@Autowired
	SwimlaneService swimlaneService;
	
	private String emailUrl;

	private boolean runActionHandlers = true;

	public Map<String, Integer> getStatusSummary(Class entity, String user) throws BpmException {
		Map<String, Integer> m = new HashMap<String, Integer>();
		/*
		 * TODO: IF RETRIEVING USER TASKS:- foreach swimlane in processDef
		 * Lookup fields assigned to swimlane eg DapProcessInstance.requestor =
		 * requestorSwimlane retrieve all PIs where above fields match the user
		 * for each PI update PI add tasks to map
		 * 
		 * IF RETRIEVING GROUP TASKS:- foreach swimlane in processDef if user is
		 * a member of the swimlane's usergroups
		 */

		/*
		 * List<Object[]>l =
		 * em.createQuery("select status,count(*) from "+entity
		 * .getName()+" group by status").getResultList(); for(Object[] row : l)
		 * { m.put((String)row[0], (Integer)row[1]); }
		 */
		return m;

	}

	public String getSwimlaneAssignee(String swimlane, ProcessInstance processInstance) throws BpmException {
		try {
			return (String) this.getSwimlaneAssigneeGetter(processInstance, swimlane).invoke(processInstance, null);
		} catch (Exception e) {
			throw new BpmException("Unable to invoke swimlane assignee getter: " + e.getMessage());
		}

	}

	private Method getSwimlaneAssigneeGetter(ProcessInstance processInstance, String swimlane) throws BpmException {
		try {
			Swimlane sw = processInstance.getProcessDefinition().getSwimlane(swimlane);
			String getStr = "get" + sw.getAssigneeProperty().substring(0, 1).toUpperCase()
					+ sw.getAssigneeProperty().substring(1);
			return processInstance.getClass().getMethod(getStr);
		} catch (Exception e) {
			throw new BpmException("Unable to get swimlane assignee getter: " + e.getMessage());
		}

	}

	public Map<String, String> getAllSwimlaneAssignees(ProcessInstance processInstance) throws BpmException {
		Map<String, String> assignees = new HashMap<String, String>();
		for (Swimlane s : processInstance.getProcessDefinition().getSwimlanes()) {
			assignees.put(s.getCode(), getSwimlaneAssignee(s.getCode(), processInstance));
		}
		return assignees;
	}


	public abstract ProcessInstance createProcessInstance() throws BpmException;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Organisation> organisationSearch(String entity, String subText) throws TooManySearchResultsException {
		String q = "from " + entity + " where lower(name) like ?";
		String p = "%" + subText.toLowerCase() + "%";
		Long c = (Long) em.createQuery("select count(*) " + q).setParameter(1, p).getSingleResult();
		if (c > 20)
			throw new TooManySearchResultsException();
		return em.createQuery("from " + entity + " where lower(name) like ?").setParameter(1, p).getResultList();
	}

	public abstract KeyDate createKeyDate();

	public abstract PhaseAssignment createPhaseAssignment();

	public abstract Long add(ProcessInstance processInstance, String add);

	public abstract void populateTaskFormModel(ModelMap modelMap, String phase, String page);

	protected void initBinder(DataBinder db) {

	}

	public abstract SwimlaneAssignment createSwimlaneAssignment();

	@Transactional
	public ProcessInstance startProcessInstance(String key) throws BpmException {
		ProcessInstance m = createProcessInstance();
		m.setProcessDefinitionKey(key);
		m.persist();
		phaseService.addPhase(m, "start", new Date());

		UserDetails ud = userService.getUserDetails();
		if (ud != null) {
			swimlaneService.assignSwimlane(m, "initiator", ud.getUsername(), null);
		}
		return m;
	}
	public abstract Attachment createAttachment();

	public abstract void save(ProcessInstance processInstance, String phase, String page, String subPage);

	public abstract void validate(ProcessInstance processInstance, String phase, String page, String subPage,
			BindingResult result, boolean requiredOnly);

	public abstract void updateStatus(KeyDate keyDate);
	
}
