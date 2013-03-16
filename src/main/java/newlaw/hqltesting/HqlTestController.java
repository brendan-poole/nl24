package newlaw.hqltesting;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HqlTestController {

	@PersistenceContext
	EntityManager em;
	
	protected Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(value = "/hql", method = RequestMethod.GET)
	public String hql(ModelMap modelMap) {
		HqlTestCommand c = new HqlTestCommand();
		modelMap.addAttribute("command", c);
		return "hql";
	}
	
	@RequestMapping(value = "/hql", method = RequestMethod.POST)
	public String hql(@ModelAttribute("command") HqlTestCommand command, ModelMap modelMap) {
		
		List l = em.createQuery(command.getQuery())
				.getResultList();
		
		modelMap.addAttribute("result", l);
		return "hql";
	}


}
