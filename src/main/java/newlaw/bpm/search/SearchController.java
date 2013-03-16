package newlaw.bpm.search;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.mail.search.SearchException;

import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.util.AppUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@SessionAttributes("command")
public class SearchController {

	protected Log log = LogFactory.getLog(this.getClass());

	private Hashtable<String, ProcessDefinition> latestProcessDefinitions = new Hashtable<String, ProcessDefinition>();

	public SearchController() {
		Hashtable<String, ProcessDefinition> allDefs = (Hashtable<String, ProcessDefinition>) AppUtils.getApplicationContext().getBean("processDefinitions");
		for(ProcessDefinition pd : allDefs.values()) {
			if(pd.getSearchService() != null)
				latestProcessDefinitions.put(pd.getCode(), pd);
		}
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(ModelMap modelMap) {
		
		modelMap.addAttribute("processDefinitions", latestProcessDefinitions);
		SearchCommand command = new SearchCommand();
		modelMap.addAttribute("command", command);
		//XXX: low - hard coded nl24
		modelMap.addAttribute("processDefinition", latestProcessDefinitions.get("nl24"));
		return "bpm/search";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@ModelAttribute("command") SearchCommand command, ModelMap modelMap) throws SearchException {
		ProcessDefinition pd = latestProcessDefinitions.get(command.getSearchProcessDefinition());
		if(command.getSearchText() != null) {
			List<ProcessInstance> results = pd.getSearchService().doSearch(command.getSearchField(), command.getSearchText());
			modelMap.addAttribute("searchResults", results);
		}
		modelMap.addAttribute("processDefinitions", latestProcessDefinitions);
		//XXX: low - hard coded nl24
		modelMap.addAttribute("processDefinition", pd);
		modelMap.addAttribute("command", command);

		return "bpm/search";
	}
		
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}


}
