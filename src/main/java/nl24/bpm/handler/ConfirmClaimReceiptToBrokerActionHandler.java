package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;
import newlaw.util.MailService;
import nl24.bpm.Nl24ProcessInstance;
import nl24.domain.Claim;

import org.springframework.beans.factory.annotation.Autowired;


public class ConfirmClaimReceiptToBrokerActionHandler extends ActionHandler {

	@Autowired MailService mailService;

	public ConfirmClaimReceiptToBrokerActionHandler() {
		LOG.debug(this);
	}
	
	@Override
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		messages.add("Receipt of claim confirmed to "+((Nl24ProcessInstance)processInstance).getClaim().getBroker());
		Claim c = ((Claim)processInstance.getData());
		String b = c.getClient().getContact().getName() +"\n";
		b += c.getClient().getContact().getAddress();
		mailService.sendMail("noreply@new-law.co.uk",c.getBroker().getEmail() , "Instruction receipt confirmation",b);
		LOG.debug("Performing action");
		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;
	}

}
