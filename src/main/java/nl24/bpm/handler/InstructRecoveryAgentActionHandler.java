package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;
import newlaw.util.MailService;
import nl24.bpm.Nl24ProcessInstance;
import nl24.bpm.Nl24ProcessInstanceService;
import nl24.bpm.Nl24SwimlaneAssignment;
import nl24.domain.Claim;
import nl24.domain.RecoveryAgent;

import org.springframework.beans.factory.annotation.Autowired;


public class InstructRecoveryAgentActionHandler extends ActionHandler {

	@Autowired
	MailService mailService;
	
	public InstructRecoveryAgentActionHandler() {
		LOG.debug(this);
	}
	
	@Override
	public List<Transition> performAction(ProcessInstance processInstance, List<String> messages) throws BpmException {
		Nl24ProcessInstanceService service = (Nl24ProcessInstanceService) getPhase().getProcessDefinition().getService();

		Nl24ProcessInstance pi = (Nl24ProcessInstance)processInstance;
		LOG.debug("Performing action");
		
		//RecoveryAgent ra = RecoveryAgent.entityManager().createQuery("from RecoveryAgent where userGroup=?",RecoveryAgent.class)
		//.setParameter(1, "DANDG").getSingleResult();
		
		RecoveryAgent ra = pi.getClaim().getBroker().getDefaultCreditServiceProvider().getRecoveryAgent();

		Nl24SwimlaneAssignment sa = (Nl24SwimlaneAssignment) service.createSwimlaneAssignment();
		sa.setUserGroup(ra.getUserGroup());
		sa.setProcessInstance(pi);
		sa.setSwimlaneCode("recoveryAgent");
		pi.getSwimlaneAssignments().add(sa);
		sa.persist();
	
		pi.getClaim().getRecoveryAgentInstruction().setRecoveryAgent(ra);
		pi.getClaim().setRecoveryAgentInstruction(pi.getClaim().getRecoveryAgentInstruction().merge());
		
		messages.add("Recovery agent set to: "+ra);

		Claim c = ((Claim)processInstance.getData());
		String b = "Client name: "+c.getClient().getContact().getName() +"\n";
		b+=c.getClient().getVehicle().getVehicleType()+"\n";
		b+=c.getClient().getVehicle().getMake()+"\n";
		b+=c.getClient().getVehicle().getModel()+"\n";
		b+=c.getClient().getVehicle().getVrn()+"\n";
		b+=c.getClient().getVehicle().getColour();
		
		mailService.sendMail("noreply@new-law.co.uk",c.getRecoveryAgentInstruction().getRecoveryAgent().getEmail() , "New recovery instruction",b);

		messages.add("Recovery instruction sent to "+pi.getClaim().getRecoveryAgentInstruction().getRecoveryAgent());
		List<Transition> tl = new ArrayList<Transition>();
		tl.add(this.getPhase().getTransitions().get(0));
		return tl;
	}

}
