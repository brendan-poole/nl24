package nl24.bpm.handler;

import java.util.ArrayList;
import java.util.List;

import newlaw.bpm.BpmException;
import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.transition.Transition;
import nl24.bpm.Nl24ProcessInstance;
import nl24.bpm.Nl24ProcessInstanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


public class ConfirmVehicleDriveableActionHandler extends ActionHandler {

	@Autowired
	Nl24ProcessInstanceService nl24ProcessInstanceService;
	
	public ConfirmVehicleDriveableActionHandler() {
		LOG.debug(this);
	}
	
	@Override
	@Transactional
	public List<Transition> performAction(ProcessInstance processInstance,  List<String> messages) throws BpmException {
		Nl24ProcessInstance pi = ((Nl24ProcessInstance)processInstance);
		List<Transition> tl = new ArrayList<Transition>();
		LOG.debug("Performing action");
		if(nl24ProcessInstanceService.isDriveable(pi.getClaim().getAccident().getVehicleDamage())) {
			messages.add("Vehicle is driveable");
			tl.add(this.getPhase().getTransition("vehicleDriveable"));
		} else {
			messages.add("Vehicle NOT driveable");
			tl.add(this.getPhase().getTransition("vehicleNotDriveable"));
		}
		
		return tl;
	}

}
