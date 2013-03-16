package newlaw.bpm.swimlane;

import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.processinstance.ProcessInstanceService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SwimlaneService {
	@Transactional
	public void assignSwimlane(ProcessInstance processInstance, String swimlane, String user, String group) {
		boolean f = false;
		for(int i = 0; i < processInstance.getSwimlaneAssignments().size(); i++) {
			SwimlaneAssignment sa = processInstance.getSwimlaneAssignments().get(i);
			if(sa.getSwimlaneCode().equals(swimlane)) {
				sa.setUserGroup(group);
				sa.setUsername(user);
				f = true;				
				processInstance.getSwimlaneAssignments().set(i, sa.merge());		
			}
		}
		ProcessInstanceService service = processInstance.getProcessDefinition().getService();
		if(!f) {
			SwimlaneAssignment sa = service.createSwimlaneAssignment();
			sa.setProcessInstance(processInstance);
			sa.setSwimlaneCode(swimlane);
			sa.setUserGroup(group);
			sa.setUsername(user);
			sa.persist();
			processInstance.getSwimlaneAssignments().add(sa);
		}
	}

}
