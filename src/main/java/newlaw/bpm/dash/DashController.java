package newlaw.bpm.dash;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpSession;

import newlaw.bpm.phase.PhaseAssignment;
import newlaw.bpm.phase.PhaseService;
import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.identities.UserDetailsService;
import newlaw.util.AppUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("dashCommand")
public class DashController {
	
	@Autowired
	PhaseService phaseService;
	
	Hashtable<String, ProcessDefinition> latestProcessDefinitions = new Hashtable<String, ProcessDefinition>();
	
	public DashController() {
		//XXX: encapsulate this...
		Hashtable<String, ProcessDefinition> allDefs = (Hashtable<String, ProcessDefinition>) AppUtils.getApplicationContext().getBean("processDefinitions");
		for(ProcessDefinition pd : allDefs.values()) {
				latestProcessDefinitions.put(pd.getCode(), pd);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String dash(ModelMap modelMap, HttpSession session) {
		
		//If returning from actioning a phase the command is still present so remove it.
		session.removeAttribute("command");
		
		DashCommand command = new DashCommand();
		if(modelMap.containsAttribute("dashCommand"))
			command = (DashCommand) modelMap.get("dashCommand");
		else {
			command = new DashCommand();
			modelMap.addAttribute("dashCommand", command);
		}
		
		if(command.getWorklistDate() == null)
			command.setWorklistDate(new Date());
		
		Calendar due = Calendar.getInstance();
		due.setTime(command.getWorklistDate());
		due.set(Calendar.HOUR_OF_DAY, 23);
		due.set(Calendar.MINUTE, 59);
		due.set(Calendar.SECOND, 59);
		due.set(Calendar.MILLISECOND, 999);

		if(command.getWorklist().equals("user")) {
			List<PhaseAssignment> pal = phaseService.findUsersPhaseAssignments(due.getTime());
			if(pal.size() > 0)
				modelMap.addAttribute("userPhaseAssignments", pal);
			else {
				pal = phaseService.findUsersGroupPhaseAssignments(due.getTime());
				if(pal.size() > 0) {
					command.setWorklist("group");
					modelMap.addAttribute("groupPhaseAssignments", pal);
				}
			}
		} 
		if(command.getWorklist().equals("group")) {
			List<PhaseAssignment> pal = phaseService.findUsersGroupPhaseAssignments(due.getTime());
			if(pal.size() > 0)
				modelMap.addAttribute("groupPhaseAssignments", pal);
			else {
				pal = phaseService.findUsersPhaseAssignments(due.getTime());
				if(pal.size() > 0) {
					command.setWorklist("user");
					modelMap.addAttribute("userPhaseAssignments", pal);
				}
			}
		}
		modelMap.addAttribute("processDefinitions", latestProcessDefinitions);
		return "bpm/dash";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String post(@ModelAttribute("dashCommand") DashCommand command, BindingResult result) {
		return "redirect:/";
	}
		@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
	}

	
}
