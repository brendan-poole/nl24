package nl24.bpm.handler;

import java.util.Date;

import newlaw.bpm.handler.DueDateHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import nl24.bpm.Nl24ProcessInstance;

public class EstimatedCompletionDueDateHandler extends DueDateHandler {

	@Override
	public Date getDueDate(ProcessInstance processInstance) {
		return ((Nl24ProcessInstance)processInstance).getClaim().getCreditServiceProviderInstruction().getEstimatedRepairsCompletionDate();
	}

	
}
