package nl24.integration;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import newlaw.bpm.BpmException;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.util.MailService;
import nl24.domain.Claim;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@Transactional
public class EasidriveIntegrationTest {

	@Autowired MailService mailService;
	

	@Test
	public void sendEmails() throws BpmException {
		List<Claim> cl = Claim.findAllClaims();
		for(Claim c : cl) {
			if(c.getCreditServiceProviderInstruction().getCreditServiceProvider() == null
					|| !c.getCreditServiceProviderInstruction().getCreditServiceProvider().getUserGroup().equals("EASIDRIVE"))
				continue;
			try {
				JAXBContext jbc = JAXBContext.newInstance(Claim.class);
				Marshaller m = jbc.createMarshaller();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				m.marshal(c, out);
				mailService.sendMailWithAttachment(
						"noreply@new-law.co.uk", c.getCreditServiceProviderInstruction().getCreditServiceProvider().getEmail(), 
						"New NL24 Instruction "+c.getReference(), "Please find details attached", c.getReference()+".xml", 
						new ByteArrayResource(out.toByteArray()));
				//Claim.entityManager().createQuery("select a from")
				
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
}
