package newlaw.bpm.processinstance;

import javax.servlet.http.HttpSession;

import newlaw.bpm.phase.PhaseCommand;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Displays the current state of a process instance; worklist, history and swimlanes.
 */
@Controller
public class ProcessInstanceController {
	
	/** The log. */
	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @param modelMap the model map
	 * @param session the session
	 * @return the string
	 */
	@RequestMapping(value = "/pi/{id}", method = RequestMethod.GET)
	public String get(@PathVariable(value="id") Long id, ModelMap modelMap, HttpSession session) {
		
		// XXX: better way of doing this?
		if(session.getAttribute("command") != null) {
			modelMap.addAttribute("messages", ((PhaseCommand)session.getAttribute("command")).getMessages());
		}
		//If returning from actioning a phase the command is still present so remove it.
		session.removeAttribute("command");

		//modelMap.addAttribute("command", command);
		modelMap.addAttribute("processInstance", ProcessInstance.findProcessInstance(id));
		return "bpm/processInstance";
	}

	/**
	 * Post.
	 *
	 * @param command the command
	 * @param modelMap the model map
	 * @return the string
	 */
	@RequestMapping(value = "/pi/{id}", method = RequestMethod.POST)
	public String post(@ModelAttribute("command") ProcessInstanceCommand command, ModelMap modelMap) {
		modelMap.addAttribute("command", command);

		return "bpm/processInstance";
	}

}
