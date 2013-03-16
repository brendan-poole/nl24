package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;
import nl24.bpm.Nl24ProcessInstance;
import nl24.bpm.Nl24ProcessInstanceService;
import nl24.bpm.Nl24SwimlaneAssignment;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


public class ResolveUserRoleActionHandler extends ActionHandler {

	public ResolveUserRoleActionHandler() {
		LOG.debug(this);
	}

	@Override
	public List<Transition> performAction(ProcessInstance processInstance, List<String> messages) throws BpmException {

		Nl24ProcessInstance pi = ((Nl24ProcessInstance)processInstance);
		Nl24ProcessInstanceService service = (Nl24ProcessInstanceService) getPhase().getProcessDefinition().getService();

		LOG.debug("Performing action");
		List<Transition> tl = new ArrayList<Transition>();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LOG.debug("User authorities: " + auth.getAuthorities());
		for(GrantedAuthority ga : auth.getAuthorities()) {
			if (ga.getAuthority().equals("NL24_Handler")) {
				tl.add(this.getPhase().getTransitions().get(0));
				
				Nl24SwimlaneAssignment sa = (Nl24SwimlaneAssignment) service.createSwimlaneAssignment();
				sa.setUsername(auth.getName());
				sa.setProcessInstance(pi);
				sa.setSwimlaneCode("fruHandler");
				pi.getSwimlaneAssignments().add(sa);
				messages.add("NL24 Handler set to "+auth.getName());
				sa.persist();
				break;
			}
		}
		if(tl.isEmpty())
			throw new BpmException("No known user authorities: "+auth);
		return tl;
	}

}
