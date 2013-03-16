package nl24.passenger;

//TODO: set @size(max=x) for all strings. aspectj?
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import newlaw.bpm.attachment.Attachment;
import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.phase.Phase;
import newlaw.bpm.phase.PhaseAssignment;
import newlaw.bpm.processdefinition.ProcessDefinition;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.processinstance.ProcessInstanceService;
import newlaw.bpm.swimlane.SwimlaneAssignment;
import newlaw.domain.Country;
import newlaw.domain.InjuryArea;
import newlaw.domain.InjuryType;
import newlaw.domain.Title;
import newlaw.domain.VehicleType;
import nl24.bpm.Nl24Attachment;
import nl24.bpm.Nl24KeyDate;
import nl24.bpm.Nl24PhaseAssignment;
import nl24.bpm.Nl24SwimlaneAssignment;
import nl24.domain.Broker;
import nl24.domain.Passenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@RooJavaBean
public class FnolPassengerService extends ProcessInstanceService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	LocalValidatorFactoryBean validator;

	@Override
	@Transactional
	public ProcessInstance createProcessInstance() {

		Nl24PassengerProcessInstance m =	 new Nl24PassengerProcessInstance();
		return m;
	}

	@Override
	public void validate(ProcessInstance processInstance, String phase, String page, String subPage, BindingResult result, boolean requiredOnly) {
		String point = phase;
		if(page != null)
			point += "."+page;

			String msg;
		if(requiredOnly)
			msg = "markRequired";
		else
			msg = "required";
		result.setNestedPath("processInstance.data");
		Nl24PassengerProcessInstance pi = ((Nl24PassengerProcessInstance) processInstance);

		if(point.equals("callToPassenger.passenger")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.contact.title",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.contact.forename",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.contact.surname",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.contact.dateOfBirth",msg);
			Passenger c = pi.getClaim().getPassenger();
			if(c.getContact().getAddress() == null || (c.getContact().getAddress().getHouseName() == null &&
					c.getContact().getAddress().getHouseNumber() == null)) {
				result.rejectValue("passenger.contact.address.houseName",null,requiredOnly ? "*" : "Enter either house name or number");
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.contact.address.street1",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.contact.address.city",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.contact.address.county",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.contact.address.postcode",msg);
		} else if(point.equals("callToPassenger.injury")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.injured",msg);
			if(pi.getClaim().getPassenger().getInjured() != null
					&& pi.getClaim().getPassenger().getInjured() == Boolean.TRUE) {
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.injury.injuryArea",msg);
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.injury.injuryType",msg);
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "passenger.injury.injuryDescription",msg);
			}
		}
		result.setNestedPath("");
	}
	
	@Override
	@Transactional 
	public void save(ProcessInstance processInstance, String phase, String page, String subPage) {
		String point = phase;
		if(page != null)
			point += "."+page;
		Nl24PassengerProcessInstance pi = ((Nl24PassengerProcessInstance) processInstance);
		PassengerClaim c = pi.getClaim();
		if(point.equals("callToPassenger.passenger"))
			c.getPassenger().merge();
		else if(point.equals("callToPassenger.injury")) {
			c.getPassenger().merge();
		}
		
	}
	
	@Override
	public KeyDate createKeyDate() {
		return new Nl24KeyDate();
	}
	
	@Override
	public PhaseAssignment createPhaseAssignment() {
		return new Nl24PhaseAssignment();
	}
	
	@Override
	public Long add(ProcessInstance processInstance, String add) {
		return null;
	}
	
	@Override
	public void populateTaskFormModel(ModelMap modelMap, String phase, String page) {
		//TODO: make sure all pages are covered here
		String point = phase;
		if(page != null)
			point += "."+page;
		if(point.equals("confirmInstructionMethod.broker")) {
			modelMap.addAttribute("brokers", Broker.findAllBrokers());
		} else if(point.equals("enterBasicDetails.client")) {
			modelMap.addAttribute("brokers", Broker.findAllBrokers());
			modelMap.addAttribute("titles", Title.values());
		} else if(point.equals("takeFullDetails.client.injury")) {
			modelMap.addAttribute("injuryAreas", InjuryArea.values());
			modelMap.addAttribute("injuryTypes", InjuryType.values());
		} else if(point.equals("confirmFault.thirdParty.passengers")) {
			modelMap.addAttribute("titles", Title.values());
		} else if(point.equals("takeBasicDetails.client.vehicle")) {
			modelMap.addAttribute("vehicleTypes", VehicleType.values());
		} else if(point.equals("takeFullDetails.witnesses")) {
			modelMap.addAttribute("titles", Title.values());
		} else if(point.equals("confirmFault.passengers")) {
			modelMap.addAttribute("titles", Title.values());
		} else if(point.equals("takeFullDetails.accidentOverview")) {
			modelMap.addAttribute("countries", Country.values());
		}
	}

	@Override
	protected void initBinder(DataBinder db) {
	    db.registerCustomEditor(Date.class, "processInstance.accident.accidentTime", new CustomDateEditor( new SimpleDateFormat( "HH:mm"), true ));
	}

	@Override
	public SwimlaneAssignment createSwimlaneAssignment() {
		return new Nl24SwimlaneAssignment();
	}


	@Override
	public Attachment createAttachment() {
		return new Nl24Attachment();
	}

	@Override
	public void updateStatus(KeyDate keyDate) {
		//keyDate.getProcessInstance().setStatus(null);
		
	}
}
