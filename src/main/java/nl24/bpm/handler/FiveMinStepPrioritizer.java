package nl24.bpm.handler;

import java.util.Calendar;
import java.util.Date;

import newlaw.bpm.handler.Prioritizer;
import newlaw.bpm.phase.PhaseAssignment;

public class FiveMinStepPrioritizer extends Prioritizer {

	@Override
	public int getPriority(PhaseAssignment pa, Date currentDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(pa.getDue());
		c.add(Calendar.MINUTE, 10);
		if(currentDate.after(c.getTime()))
			return 2;
		else {
			c.add(Calendar.MINUTE, -5);
			if(currentDate.after(c.getTime()))
				return 1;
			else
				return 0;
		}
	}
}
