package newlaw.bpm.handler;

import java.util.Date;

import newlaw.bpm.processinstance.ProcessInstance;

public abstract class DueDateHandler {

	public abstract Date getDueDate(ProcessInstance processInstance);
}
