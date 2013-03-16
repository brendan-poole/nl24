package nl24.bpm.handler;

import java.util.Date;

import newlaw.bpm.handler.DueDateHandler;
import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.processinstance.ProcessInstance;
import nl24.bpm.Nl24ProcessInstance;

// Works for both driver and passenger claims

public class OneMonthFollowingInjuryConfirmationDueDateHandler extends DueDateHandler {

	@Override
	public Date getDueDate(ProcessInstance processInstance) {
		KeyDate kd = KeyDate.entityManager().createQuery("select a from KeyDate a where a.processInstance=? order by a.triggered", KeyDate.class)
				.setParameter(1, processInstance).setMaxResults(1).getSingleResult();
		return kd.getTriggered();
	}

	
}
