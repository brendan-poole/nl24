package nl24.domain;

import java.util.List;

import junit.framework.Assert;

import newlaw.domain.Person;
import nl24.bpm.Nl24ProcessInstance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class ProcessInstanceTest {
	@Test
	//@Rollback(false)
	public void findProcessInstancesByClientNamePatternsTest() {
		Nl24ProcessInstance npi = new Nl24ProcessInstance();
		((Nl24Person)npi.getClaim().getClient().getContact()).setForename("Suzanne");
		((Nl24Person)npi.getClaim().getClient().getContact()).setSurname("Leakey");
		npi.persist();
		String name = "  leakey, Suzanne        ";
		name = name.replaceAll(",", "");
		String[] names = name.trim().split(" +");
		if(names.length == 1) {
			String n = names[0];
			names = new String[2];
			names[0] = n;
			names[1] = "";
		}
			
		List<Nl24ProcessInstance> l = Nl24ProcessInstance.entityManager().createNamedQuery(

				"findByClientNamePatterns", Nl24ProcessInstance.class)
				.setParameter("name1", "%"+names[0]+"%")
				.setParameter("name2", "%"+names[1]+"%")
				.getResultList();
		for(Nl24ProcessInstance pi : l)
			System.out.println(pi.getClaim().getClient().getContact().getName());
		
		Assert.assertTrue(l.get(0).getClaim().getClient().getContact().getName().equals("Suzanne Leakey"));
	}

}
