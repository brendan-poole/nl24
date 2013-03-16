package newlaw.dap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import newlaw.bpm.attachment.Attachment;
import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.phase.PhaseAssignment;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.processinstance.ProcessInstanceService;
import newlaw.bpm.swimlane.SwimlaneAssignment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

@Service
public class DapService extends ProcessInstanceService {

	@PersistenceContext
	EntityManager em;
	
	@Override
	public ProcessInstance createProcessInstance() {
		return new DapProcessInstance();
	}
	
	@Override
	public KeyDate createKeyDate() {
		return new DapKeyDate();
	}

	@Override
	public PhaseAssignment createPhaseAssignment() {
		return new DapPhaseAssignment();
	}

	@Override
	public SwimlaneAssignment createSwimlaneAssignment() {
		return new DapSwimlaneAssignment();
	}

	@Override
	public Long add(ProcessInstance processInstance, String add) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void populateTaskFormModel(ModelMap modelMap, String phase, String page) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Attachment createAttachment() {
		return new DapAttachment();
	}

	@Override
	public void save(ProcessInstance processInstance, String phase, String page, String subPage) {
		DapInstruction di = (DapInstruction) processInstance.getData();
		di.merge();
		
	}

	@Override
	public void validate(ProcessInstance processInstance, String phase, String page, String subPage,
			BindingResult result, boolean requiredOnly) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStatus(KeyDate keyDate) {
		// TODO Auto-generated method stub
		
	}
}
