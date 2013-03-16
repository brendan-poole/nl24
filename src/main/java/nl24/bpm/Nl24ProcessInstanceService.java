package nl24.bpm;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import newlaw.bpm.attachment.Attachment;
import newlaw.bpm.keydate.KeyDate;
import newlaw.bpm.phase.PhaseAssignment;
import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.processinstance.ProcessInstanceService;
import newlaw.bpm.swimlane.SwimlaneAssignment;
import newlaw.domain.Country;
import newlaw.domain.InjuryArea;
import newlaw.domain.InjuryType;
import newlaw.domain.Person;
import newlaw.domain.Title;
import newlaw.domain.VehicleType;
import nl24.domain.Accident;
import nl24.domain.Broker;
import nl24.domain.Claim;
import nl24.domain.CreditServiceProvider;
import nl24.domain.Nl24Person;
import nl24.domain.Party;
import nl24.domain.Passenger;
import nl24.domain.Police;
import nl24.domain.RecoveryAgent;
import nl24.domain.Solicitor;
import nl24.domain.VehicleCategory;
import nl24.domain.VehicleDamage;
import nl24.domain.Witness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@RooJavaBean
public class Nl24ProcessInstanceService extends ProcessInstanceService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	LocalValidatorFactoryBean validator;

	private String repairersCoreHoursStart;
	private String repairersCoreHoursEnd;

	public Boolean isWithinRepairersCoreHours(Date date) {
		// unused now  as always instructing
		try {
			SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss.SSS");
			Date coreHoursStart;
			coreHoursStart 
			= f.parse(repairersCoreHoursStart + ":00.000");
			Date coreHoursEnd = f.parse(repairersCoreHoursEnd + ":00.000");
			if (f.format(date).compareTo(f.format(coreHoursStart)) < 0
					|| f.format(coreHoursEnd).compareTo(f.format(date)) < 0) {
				return Boolean.FALSE;
			} else {
				return Boolean.TRUE;
			}
		} catch (ParseException e) {
			/*Person p = new Person();
			p.setEmail("brendan.poole@new-law.co.uk");
			p.sendMessage("fnol error", "Core hours parse error");*/
			return null;
		}
	}

	@Override
	@Transactional
	public ProcessInstance createProcessInstance() {
		Nl24ProcessInstance m =	 new Nl24ProcessInstance();
		return m;
	}

	
	public Boolean isDriveable(VehicleDamage vehicleDamage) {
		if (vehicleDamage.getVehicleUnsecure() == null || vehicleDamage.getAnyWater() == null
				|| vehicleDamage.getAnythingHangingOffVehicle() == null
				|| vehicleDamage.getWheelsOnAndStraight() == null
				|| vehicleDamage.getAnyDamageToLights() == null
				|| vehicleDamage.getAnyDamageToBackLights() == null
				|| vehicleDamage.getAnyFluidCommingFromVehicle() == null
				|| vehicleDamage.getAnyWindowsBroken() == null)

			return null;
		if (vehicleDamage.getVehicleUnsecure() != Boolean.FALSE
				|| vehicleDamage.getAnyWater() != Boolean.FALSE
				|| vehicleDamage.getAnythingHangingOffVehicle() != Boolean.FALSE
				|| vehicleDamage.getWheelsOnAndStraight() != Boolean.TRUE
				|| vehicleDamage.getAnyDamageToLights() != Boolean.FALSE
				|| vehicleDamage.getAnyDamageToBackLights() != Boolean.FALSE
				|| vehicleDamage.getAnyFluidCommingFromVehicle() != Boolean.FALSE
				|| vehicleDamage.getAnyWindowsBroken() != Boolean.FALSE) {

			return Boolean.FALSE;

		} else {

			return Boolean.TRUE;
		}
		
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
		Object o;
		
		result.setNestedPath("processInstance.data");
		Nl24ProcessInstance pi = ((Nl24ProcessInstance) processInstance);
		if(point.equals("confirmInstructionMethod.broker")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "broker",msg);
		} else if(point.equals("takeBasicDetails.client") 
				|| point.equals("takeBasicDetails2.client")) {
			Party c = (Party) pi.getClaim().getClient();
			Person p = (Person)c.getContact();
			validator.validate(pi.getClaim().getClient(), result);
			if(p.getHomeTelephone() == null && p.getMobileTelephone() == null && p.getWorkTelephone() == null)
				result.rejectValue("client.contact.mobileTelephone", null, "Enter at least one telephone number");
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.title",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.forename",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.surname",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.dateOfBirth",msg);
			Date d = ((Nl24Person)(pi.getClaim().getClient().getContact())).getDateOfBirth();
			if(d != null) { 
				if(d.after(new Date()))
					result.rejectValue("client.contact.dateOfBirth",null,"Only a date in the past is allowed");
				else {
					Calendar e = Calendar.getInstance();
					e.setTime(new Date());
					e.add(Calendar.YEAR, -110);
					if(d.before(e.getTime()))
						result.rejectValue("client.contact.dateOfBirth",null,"Date of birth must be within 110 years of current date");
				}
				if(c.getContact().getAddress() == null || (c.getContact().getAddress().getHouseName() == null &&
							c.getContact().getAddress().getHouseNumber() == null)) {
					result.rejectValue("client.contact.address.houseName",null,requiredOnly ? "*" : "Enter either house name or number");
				}
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.address.street1",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.address.city",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.address.county",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.address.postcode",msg);
		} else if(point.equals("takeBasicDetails.client.vehicle")
				|| point.equals("takeBasicDetails2.client.vehicle")) {
			validator.validate(pi.getClaim().getClient().getVehicle(), result);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.vehicle.vehicleType",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.vehicle.vrn",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.vehicle.make",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.vehicle.model",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.vehicle.engineSize",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.vehicle.colour",msg);
		} else if(point.equals("takeBasicDetails.broker") || point.equals("takeBasicDetails2.broker")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "brokerPolicyNumber",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.insurancePolicyNumber",msg);
		} else if(point.equals("vehicleDriveableQuestionnaire.accident.vehicleDamage")) {
			if(!requiredOnly && isDriveable(pi.getClaim().getAccident().getVehicleDamage()) == null)
				result.addError(new ObjectError("processInstance.accident.vehicleDamage", "Complete all Vehicle damage fields"));
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.vehicleDamageDetails",msg);
		} else if(point.equals("takeFullDetails.accidentOverview")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.accidentTime",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.accidentDate",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.country",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.town",msg);
			if(pi.getClaim().getAccident().getAccidentDate() != null) {
				if(pi.getClaim().getAccident().getAccidentDate().after(new Date()))
					result.rejectValue("accident.accidentDate",null,"Only a date in the past is allowed");
				else {
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					c.add(Calendar.YEAR, -1);
					if(pi.getClaim().getAccident().getAccidentDate().before(c.getTime()))
						result.rejectValue("accident.accidentDate",null,"Accident date must be within one year of current date");
				}
			}
			
		} else if(point.equals("confirmFault.accidentCircumstances")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.accidentCircumstances",msg);
			Accident a = pi.getClaim().getAccident();
			if(!requiredOnly && !(a.isAccidentChangingLanes() || a.isAccidentInCarPark() 
					|| a.isAccidentOnRoundabout() || a.isConcertinaCollision() 
					|| a.isVehicleHitInRear() ||  a.isVehicleHitSide() || a.isVehicleHitWhilstParked())) {
				result.addError(new ObjectError("command", "Enter nature of accident"));
			}
			if(!requiredOnly && !(a.isWeatherSun() || a.isWeatherRain() || a.isWeatherSnow() || a.isWeatherIce()
					|| a.isWeatherFog() || a.getOtherWeatherDetails() != null)) {
				result.addError(new ObjectError("processInstance.accident.vehicleDamage", "Enter weather"));
			}
			if(!requiredOnly && !(a.isDry() || a.isWet() || a.isSnow() || a.isIce() || a.isMud() || a.isOil() 
					|| a.getOtherConditions() != null)) {
				result.addError(new ObjectError("processInstance.accident.vehicleDamage", "Enter conditions"));
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.claimantPositionInVehicle",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.purposeOfJourney",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.journeyDuration",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.claimantCollectedLocation",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.seatBelt",msg);
			
		} else if(point.equals("confirmFault.thirdParty.driver")) {
			if(pi.getClaim().getThirdParty().getInsurer() != null)
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "thirdParty.insurer.name",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "thirdPartyInvolved",msg);
		} else if(point.equals("takeFullDetails.accidentOverview")) {
			// NO validation required according to docs.
		} else if(point.equals("takeFullDetails.police")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "accident.policeReported",msg);
			if(pi.getClaim().getAccident().getPoliceReported() != null 
					&& pi.getClaim().getAccident().getPoliceReported() == Boolean.TRUE)
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "policeAttendance.comments",msg);
			if(pi.getClaim().getPoliceAttendance().getPolice() != null) {
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "policeAttendance.police.name",msg);
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "policeAttendance.police.address.city",msg);
			}
		} else if(point.equals("takeFullDetails.thirdParty.vehicle")) {
		} else if(point.equals("takeFullDetails.witnesses")) {
			// NO validation required according to docs.
		} else if(point.equals("takeFullDetails.client.injury")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.injured",msg);
			if(pi.getClaim().getClient().getInjured() != null
					&& pi.getClaim().getClient().getInjured() == Boolean.TRUE) {
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.injury.injuryArea",msg);
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.injury.injuryType",msg);
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.injury.injuryDescription",msg);
			}
		} else if(point.equals("takeFullDetails.client.otherVehicle")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.accessToOtherVehicle",msg);		
		} else if(point.equals("enterVehicleLocation.recovery")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "recoveryAgentInstruction.vehicleLocation",msg);
		} else if(point.equals("recoverVehicle.recovery")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "recoveryAgentInstruction.vehicleLocation",msg);
		} else if(point.equals("cspEnterHireDetails.hireProvider") || point.equals("enterHireDetails.hireProvider")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.hireProvider",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.vehicleCategoryGroupRating",msg);
			if(pi.getClaim().getCreditServiceProviderInstruction().getHireProvider() != null)
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.hireProvider.name",msg);
		} else if(point.equals("enterRepairsBookingDetails.repairsBooking") 
				|| point.equals("cspEnterRepairsBookingDetails.repairsBooking") ) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.repairerJobNumber",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.repairsStatus",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.estimatedRepairsCompletionDate",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.repairer",msg);
			if(pi.getClaim().getCreditServiceProviderInstruction().getRepairer() != null)
				ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.repairer.name",msg);
		} else if(point.equals("chaseVehicleRepairBooking.booking")) {
		} else if(point.equals("enterTotalLossDetails.totalLoss")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.totalLossSalvageValue",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.totalLossSalvageValueAgreed",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.totalLossSalvageValueAuthorised",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "creditServiceProviderInstruction.totalLossValuation",msg);
		} else if(point.equals("takeContactDetails.client")) {
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.title",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.forename",msg);
			ValidationUtils.rejectIfEmptyOrWhitespace(result, "client.contact.surname",msg);
			Person p = (Person) pi.getClaim().getClient().getContact();
			if(p.getHomeTelephone() == null && p.getMobileTelephone() == null && p.getWorkTelephone() == null)
				result.rejectValue("client.contact.mobileTelephone", null, requiredOnly ? "*" : "Enter at least one telephone number");
		}
		
		result.setNestedPath("");
	}
	
	@Override
	@Transactional 
	public void save(ProcessInstance processInstance, String phase, String page, String subPage) {
		String point = phase;
		if(page != null)
			point += "."+page;
		Nl24ProcessInstance pi = ((Nl24ProcessInstance) processInstance);
		Claim c = pi.getClaim();
		if(point.equals("confirmInstructionMethod.broker"))
			c.merge();
		if(point.equals("takeBasicDetails.client")
				|| point.equals("takeBasicDetails2.client")) {
			c.getClient().merge();
		} else if(point.equals("takeBasicDetails.client.vehicle")
				|| point.equals("takeBasicDetails2.client.vehicle")) {
			c.getClient().getVehicle().merge();
		
		} else if(point.equals("takeBasicDetails.broker")
				|| point.equals("takeBasicDetails2.broker")) {
			c.getClient().merge();
			c.merge();
		} else if(point.equals("vehicleDriveableQuestionnaire.accident.vehicleDamage")) {
			pi.getClaim().getClient().merge();
			pi.getClaim().getAccident().merge();
		} else if(point.equals("confirmFault.accidentCircumstances")) {
			pi.getClaim().getAccident().merge(); 
		} else if(point.equals("confirmFault.passengers")) {
			for(int i = 0; i < pi.getClaim().getClient().getPassengers().size(); i++) {
				if(pi.getClaim().getClient().getPassengers().get(i).getId()
						.equals(Long.valueOf(subPage))) {
					pi.getClaim().getClient().getPassengers().get(i).merge();
					break;
				}
			}
		} else if(point.equals("confirmFault.thirdParty.vehicle")) {
			pi.getClaim().getThirdParty().getVehicle().merge();
		} else if(point.equals("confirmFault.thirdParty.passengers")) {
			for(int i = 0; i < pi.getClaim().getThirdParty().getPassengers().size(); i++) {
				if(pi.getClaim().getThirdParty().getPassengers().get(i).getId()
						.equals(Long.valueOf(subPage))) {
					pi.getClaim().getThirdParty().getPassengers().get(i).merge();
					break;
				}
			}
		} else if(point.equals("confirmFault.thirdParty.driver")) {
			pi.getClaim().getThirdParty().merge();
			pi.getClaim().merge();
		} else if(point.equals("takeFullDetails.accidentOverview")) {
			pi.getClaim().getAccident().merge();
		} else if(point.equals("takeFullDetails.police")) {
			pi.getClaim().getAccident().merge();
			pi.getClaim().getPoliceAttendance().merge();
		} else if(point.equals("takeFullDetails.thirdParty.vehicle")) {
			pi.getClaim().getThirdParty().getVehicle().merge();
		} else if(point.equals("takeFullDetails.witnesses")) {
			for(int i = 0; i < pi.getClaim().getWitnesses().size(); i++) {
				if(pi.getClaim().getWitnesses().get(i).getId()
						.equals(Long.valueOf(subPage))) {
					pi.getClaim().getWitnesses().set(i,
							pi.getClaim().getWitnesses().get(i).merge());
					break;
				}
			}
		} else if(point.equals("takeFullDetails.client.injury")) {
			pi.getClaim().getClient().merge();
		} else if(point.equals("takeFullDetails.client.otherVehicle")) {
			pi.getClaim().getClient().merge();
		} else if(point.equals("enterVehicleLocation.recovery")) {
			pi.getClaim().getRecoveryAgentInstruction().merge();
		} else if(point.equals("recoverVehicle.recovery")) {
			pi.getClaim().getRecoveryAgentInstruction().merge();
		} else if(point.equals("enterHireDetails.hireProvider")) {
			pi.getClaim().getCreditServiceProviderInstruction().merge();
		} else if(point.equals("cspEnterHireDetails.hireProvider")) {
			pi.getClaim().getCreditServiceProviderInstruction().merge();
		} else if(point.equals("enterRepairsBookingDetails.repairsBooking")) {
			pi.getClaim().getCreditServiceProviderInstruction().merge();
		} else if(point.equals("cspEnterRepairsBookingDetails.repairsBooking")) {
			pi.getClaim().getCreditServiceProviderInstruction().merge();
		} else if(point.equals("chaseVehicleRepairBooking.repairs")) {
			pi.getClaim().getCreditServiceProviderInstruction().merge();
		} else if(point.equals("chaseVehicleRepair.repairs")) {
			pi.getClaim().getCreditServiceProviderInstruction().merge();
		} else if(point.equals("enterTotalLossDetails.totalLoss")) {
			pi.getClaim().getCreditServiceProviderInstruction().merge();
		} else if(point.equals("takeContactDetails.client")) {
			pi.getClaim().getClient().merge();
		} else if(point.equals("midSearch.midSearch")) {
			pi.getClaim().getClient().merge();
			pi.getClaim().merge();
		} else if(point.equals("enterHireRejectionReason.hire")) {
			pi.getClaim().getCreditServiceProviderInstruction().merge();
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
		Claim pi = ((Nl24ProcessInstance) processInstance).getClaim();
		if(add.equals("passenger")) {
			Passenger p = pi.getClient().addPassenger();
			p = p.merge();
			return p.getId();
		} else if(add.equals("witness")) {
			Witness w = pi.addWitness();
			w = w.merge();
			return w.getId();
		} else if(add.equals("thirdParty.passenger")) {
			Passenger p = pi.getThirdParty().addPassenger();
			p = p.merge();
			return p.getId();
		}
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
		} else if(point.equals("enterHireDetails.hireProvider")) {
			modelMap.addAttribute("vehicleCategoryGroupRatings", VehicleCategory.values());
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
		String p = keyDate.getPhaseCode();
		String t = keyDate.getTransitionCode();
		if((p.equals("chaseRecovery") && t.equals("vehicleRecovered")
				|| p.equals("recoverVehicle") && t.equals("vehicleRecovered"))) {
			keyDate.getProcessInstance().setStatus(Nl24ClaimStatus.Hire);
		} else {
			keyDate.getProcessInstance().setStatus(Nl24ClaimStatus.Recovery);
		}
	}
	
	public void initData() {
		if(CreditServiceProvider.countCreditServiceProviders() == 0) {

			RecoveryAgent ra = new RecoveryAgent();
			ra.setName("D & G");
			ra.setUserGroup("DANDG");
			ra.setEmail("brendan.poole@new-law.co.uk");
			ra.persist();

			CreditServiceProvider da = new CreditServiceProvider();
			da.setName("Drive Assist");
			da.setUserGroup("DRIVE_ASSIST");
			da.setEmail("brendan.poole@new-law.co.uk");
			da.setTelephone("0844 8004 004");
			da.setExportHandlerBean("driveAssistSendClaimHandler");
			da.setRecoveryAgent(ra);
			da.persist();
		
			Broker b = new Broker();
			b.setCode("EMU");
			b.setName("Emu Insurance");
			//b.setEmail("philip@emuinsurance.co.uk");
			b.setEmail("brendan.poole@new-law.co.uk");
			b.setDefaultCreditServiceProvider(da);
			b.persist();

			b = new Broker();
			b.setCode("FAL");
			b.setName("Falcon Insurance");
			//b.setEmail("ronnie.roberts@falconinsuramce.co.uk");
			b.setEmail("brendan.poole@new-law.co.uk");
			b.setDefaultCreditServiceProvider(da);
			b.persist();
	
			ra = new RecoveryAgent();
			ra.setName("Easidrive");
			ra.setUserGroup("EASIDRIVE_RA");
			ra.setEmail("brendan.poole@new-law.co.uk");
			ra.persist();
			
			da = new CreditServiceProvider();
			da.setName("Easidrive");
			da.setUserGroup("EASIDRIVE");
			da.setEmail("brendan.poole@new-law.co.uk");
			da.setTelephone("0844 8004 005");
			da.setExportHandlerBean("xmlEmailSendClaimHandler");
			da.setRecoveryAgent(ra);
			da.persist();
	
			b = new Broker();
			b.setCode("ARK");
			b.setName("Arkwright Insurance");
			//b.setEmail("elyas@arkwrightinsurance.co.uk");
			b.setEmail("brendan.poole@new-law.co.uk");
			b.setDefaultCreditServiceProvider(da);
			b.persist();
			
			b = new Broker();
			b.setCode("CLA");
			b.setName("CLA Insurance");
			//b.setEmail("adrian.littler@cla.uk.net");
			b.setEmail("brendan.poole@new-law.co.uk");
			b.setDefaultCreditServiceProvider(da);
			b.persist();
			
			da = new CreditServiceProvider();
			da.setName("S & G Response");
			da.setUserGroup("SANDG");
			da.setEmail("brendan.poole@new-law.co.uk");
			da.setTelephone("0844 8004 006");
			da.persist();
			
			Police p = new Police();
			p.setName("Test Police");
			p.persist();
		}
		
		if(Solicitor.countSolicitors() == 0) {
			Solicitor s = new Solicitor();
			s.setName("NewLaw");
			s.setUserGroup("NEWLAW");
			s.persist();
			
			s = new Solicitor();
			s.setName("GT Law");
			s.setUserGroup("GT_LAW");
			s.setEmail("brendan.poole@new-law.co.uk");
			s.persist();
		}

	}

}
