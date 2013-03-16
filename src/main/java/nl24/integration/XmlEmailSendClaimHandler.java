package nl24.integration;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import newlaw.util.MailService;
import nl24.domain.Claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;

public class XmlEmailSendClaimHandler extends SendClaimHandler {

	@Autowired MailService mailService;
	
	@Override
	public void send(Claim claim) {
		try {
			JAXBContext jbc = JAXBContext.newInstance(Claim.class);
			Marshaller m = jbc.createMarshaller();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			m.marshal(claim, out);
			mailService.sendMailWithAttachment(
					"noreply@new-law.co.uk", claim.getCreditServiceProviderInstruction().getCreditServiceProvider().getEmail(), 
					"New NL24 Instruction: "+claim.getReference(), "Please find details attached", claim.getReference()+".xml", 
					new ByteArrayResource(out.toByteArray()));
			//Claim.entityManager().createQuery("select a from")
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
