package nl24.domain;

import java.util.Hashtable;

import junit.framework.Assert;
import newlaw.bpm.phase.Phase;
import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.util.AppUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class ProcessDefinitionTest {
	@Test
	//@Rollback(false)
	public void initTest() {
		// Generate a list of the phases.
		Hashtable<String, ProcessDefinition> procDefs = (Hashtable) AppUtils.getApplicationContext().getBean("processDefinitions");
		ProcessDefinition pd = procDefs.get("fnol1");
		for(Phase p: pd.getPhases()) {
			System.out.println(p.getName()+"\t"+(p.getSwimlane() == null ? "" : p.getSwimlane().getName()));
		}
		Assert.assertTrue(true);
	}

}
