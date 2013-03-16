package newlaw.bpm;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import newlaw.bpm.processinstance.ProcessInstance;
import nl24.bpm.Nl24ProcessInstance;
import nl24.bpm.Nl24ProcessInstanceService;
import nl24.domain.Broker;
import nl24.domain.Claim;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/test-applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class ProcessServiceTest {
	
	@Autowired
	private Nl24ProcessInstanceService nl24ProcessInstanceService;

	@Test 
	public void startProcessInstance() {
		Nl24ProcessInstance pi = (Nl24ProcessInstance) nl24ProcessInstanceService.startProcessInstance("test1");
		Assert.assertEquals("initiator", pi.getSwimlaneAssignments().get(0).getSwimlaneCode());
		Assert.assertEquals("start", pi.getPhaseAssignments().get(0).getPhaseCode());
		Assert.assertEquals(0,pi.getKeyDates().size());
		Assert.assertEquals(1, pi.getSwimlaneAssignments().size());
		Assert.assertEquals(1, pi.getPhaseAssignments().size());
	}
	
	@Test
	public void trigger() {
		List<String> messages = new ArrayList<String>();
		Nl24ProcessInstance pi = (Nl24ProcessInstance) nl24ProcessInstanceService.startProcessInstance("test1");
		//nl24ProcessInstanceService.trigger(pi, messages, "started", "start");
		Assert.assertTrue(pi.getKeyDates().get(0).getPhaseCode().equals("start"));
		Assert.assertEquals(1,pi.getKeyDates().size());
		Assert.assertEquals("winston.smith", pi.getSwimlaneAssignments().get(0).getUsername());
		Assert.assertEquals(1, pi.getSwimlaneAssignments().size());
		Assert.assertEquals("sayHello", pi.getPhaseAssignments().get(0).getPhaseCode());

	}
	
	@Test
	@Rollback(false)
	public void RESET() {
		for(Broker b : Broker.findAllBrokers()) {
			b.remove();
		}
		for(ProcessInstance pi : ProcessInstance.findAllProcessInstances()) {
			pi.remove();
		}
		//assert(ProcessInstance.countProcessInstances() == 0);
	}

}
