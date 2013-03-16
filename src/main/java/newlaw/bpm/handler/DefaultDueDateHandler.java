package newlaw.bpm.handler;

import java.util.Date;

import newlaw.bpm.processinstance.ProcessInstance;

public class DefaultDueDateHandler extends DueDateHandler {

	@Override
	public Date getDueDate(ProcessInstance processInstance) {
		return new Date();
	}

}
