package nl24.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.identities.UserDetailsService;
import newlaw.util.AppUtils;
import nl24.domain.Broker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReportController {

	protected Log log = LogFactory.getLog(this.getClass());

	@Autowired
	UserDetailsService userService;
	
	private Nl24ReportService reportService;
	
	public ReportController() {
		//XXX: encapsulate this...
		Hashtable<String, ProcessDefinition> latestProcessDefinitions = new Hashtable<String, ProcessDefinition>();
		
		Hashtable<String, ProcessDefinition> allDefs = (Hashtable<String, ProcessDefinition>) AppUtils.getApplicationContext().getBean("processDefinitions");
		for(ProcessDefinition pd : allDefs.values()) {
				latestProcessDefinitions.put(pd.getCode(), pd);
		}
		reportService  = (Nl24ReportService) latestProcessDefinitions.get("nl24").getReportService();
	}

	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
	}

	@RequestMapping(value = "/reports/broker/claimsPortfolio", method = RequestMethod.GET)
	public String claimsPortfolio(
			@ModelAttribute("command") ReportCommand command,
			@RequestParam(value="drill", required=false) String drilledItem,			
			ModelMap modelMap) {

		UserDetails ud = userService.getUserDetails();

		Hashtable<String,String> items = new Hashtable<String,String>();
		Hashtable<String,List<KeyDate>> lists = new Hashtable<String,List<KeyDate>>();
		items.put("hireCommenced", "Hire commenced");
		for(String code : items.keySet())
			lists.put(code, findItemBetweenDate(code, command.getBroker(), command.getStartDate(), command.getEndDate()));
		modelMap.addAttribute("items", items);
		modelMap.addAttribute("lists", lists);
		modelMap.addAttribute("drilledItem", drilledItem);
		return "report/claimsPortfolio";
	}
	
	@RequestMapping(value = "/reports/broker", method = RequestMethod.GET)
	public String criteria(ModelMap modelMap) {
		ReportCommand c = new ReportCommand();
		modelMap.addAttribute("command", c);
		modelMap.addAttribute("brokers", Broker.findAllBrokers());
		modelMap.addAttribute("reportTypes", ReportType.values());
		return "report/criteria";
	}

	public List<KeyDate> findItemBetweenDate(String item, Broker broker, Date start, Date end) {
		List<KeyDate> l = new ArrayList<KeyDate>();
		if(item.equals("hireCommenced")) {
			l = reportService.findKeyDatesBetweenDatesByBroker(broker,"chaseHireDeployed" ,"clientPlacedInVehicle", start, end);
			l.addAll(reportService.findKeyDatesBetweenDatesByBroker(broker,"supplyHireVehicle" ,"clientPlacedInVehicle", start, end));
		}
		return l;
	}
}
