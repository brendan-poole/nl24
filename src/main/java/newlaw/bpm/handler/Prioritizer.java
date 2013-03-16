package newlaw.bpm.handler;

import java.util.Date;

import newlaw.bpm.phase.PhaseAssignment;


public abstract class Prioritizer {

	public abstract int getPriority(PhaseAssignment pa, Date currentDate);
}
