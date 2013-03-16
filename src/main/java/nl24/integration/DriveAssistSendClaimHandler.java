package nl24.integration;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import newlaw.identities.LdapService;
import nl24.bpm.Nl24ProcessInstanceService;
import nl24.domain.Claim;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class DriveAssistSendClaimHandler extends SendClaimHandler {

	protected final static Log log = LogFactory.getLog(DriveAssistSendClaimHandler.class);
	
	private DatatypeFactory dataTypeFactory;
	
	private String address;
	
	private String username;
	
	private String password;
	
	@Autowired
	Nl24ProcessInstanceService nl24ProcessInstanceService;
	
	@Autowired
	private LdapService ldapService;
	
	public DriveAssistSendClaimHandler() throws DatatypeConfigurationException {
		dataTypeFactory = DatatypeFactory.newInstance();		
	}
	
	@Override
	public void send(Claim claim) {
		/*
		uk.co.driveassist.Claim driveAssistClaim = new uk.co.driveassist.Claim();
		
		copyData(driveAssistClaim, claim);
		JAXBContext jbc;
		
		try {
			jbc = JAXBContext.newInstance(uk.co.driveassist.Claim.class);

		Marshaller m = jbc.createMarshaller();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		m.marshal(driveAssistClaim, out);
		
		log.debug(out.toString());
		
		Import_Type params = new Import_Type();
		params.setUserName(username);
		params.setPassword(password);
		params.setData(out.toString());
		
		Import_Service is = new Import_Service();
		Import i = is.getImportSOAP();
		ImportResponse resp = i.importt(params);
		log.debug(resp.getOut());
		
		} catch (JAXBException e) {
			throw new IntegrationException(e);
		}
	}

	private void copyData(uk.co.driveassist.Claim dc, Claim nc) {
		dc.setAccidentDate(convDate(nc.getAccident().getAccidentDate())); 
		dc.setAccidentCircumstances(nc.getAccident().getAccidentCircumstances());
		dc.setAccidentLocation(nc.getAccident().getLocation());
		dc.setAccidentPassengers(nc.getClient().getFirstPassenger().isPopulated());
		dc.setAccidentPoliceName(nc.getPoliceAttendance().getReportingOfficerName());
		dc.setAccidentPoliceNo(nc.getPoliceAttendance().getReferenceNumber());
		if(nc.getPoliceAttendance().getPolice() != null)
			dc.setAccidentPoliceStation(nc.getPoliceAttendance().getPolice().getName());
		dc.setAccidentRoadCond(nc.getAccident().getOtherConditions()); //TODO:
		dc.setAccidentSeatBelt(nc.getAccident().getSeatBelt());
		dc.setAccidentTime(convDate(nc.getAccident().getAccidentTime()));
		dc.setAccidentWitnesses(nc.getFirstWitness().isPopulated());
		dc.setAccidentWeatherCond(nc.getAccident().getOtherWeatherDetails()); //TODO:
		
		ClientDetail cd = new ClientDetail();
		String x = nc.getClient().getContact().getAddress().getHouseName();
		String y = "";
		if(x != null)
			y += x;
		x = nc.getClient().getContact().getAddress().getHouseNumber();
		if(x != null)
			y += x;
		cd.setAddress1(y + " " + nc.getClient().getContact().getAddress().getStreet1());
		cd.setAddress2(nc.getClient().getContact().getAddress().getStreet2());
		cd.setAddress3(nc.getClient().getContact().getAddress().getDistrict());
		cd.setAddress4(nc.getClient().getContact().getAddress().getCity());
		cd.setPostcode(nc.getClient().getContact().getAddress().getPostcode());
		Nl24Person pe = (Nl24Person) nc.getClient().getContact();
		cd.setForename(pe.getForename());
		cd.setSurname(pe.getSurname());
		cd.setEmail(nc.getClient().getContact().getEmail());
		cd.setMobilePhone(pe.getMobileTelephone());
		cd.setInsurance(new InsuranceDetail());
		if(nc.getClient().getInsurer() != null && nc.getClient().getInsurer().getAddress() != null) {
			cd.getInsurance().setBroker(new Broker());
			String h = "";
			if(nc.getClient().getInsurer().getAddress().getHouseName() != null)
				h+= nc.getClient().getInsurer().getAddress().getHouseName();
			if(nc.getClient().getInsurer().getAddress().getHouseNumber() != null) 
				h += nc.getClient().getInsurer().getAddress().getHouseNumber();
			
			cd.getInsurance().getBroker().setAddress1(h + nc.getClient().getInsurer().getAddress().getStreet1());
			cd.getInsurance().getBroker().setAddress2(nc.getClient().getInsurer().getAddress().getStreet2());
			cd.getInsurance().getBroker().setAddress3(nc.getClient().getInsurer().getAddress().getDistrict());
			cd.getInsurance().getBroker().setAddress4(nc.getClient().getInsurer().getAddress().getCity());
			cd.getInsurance().getBroker().setPostcode(nc.getClient().getInsurer().getAddress().getPostcode());
		}
		KeyDate kd = KeyDate.entityManager().createQuery("select a from KeyDate a where a.phaseCode='confirmVehicleDriveable' and a.processInstance=:pi", KeyDate.class)
			.setParameter("pi", nc.getProcessInstance()).getSingleResult();
		
		cd.setVehicleColour(nc.getClient().getVehicle().getColour());
		
		cd.setVehicleDriveable(kd.getTransitionCode().equals("vehicleDriveable"));
		cd.setVehicleMake(nc.getClient().getVehicle().getMake());
		cd.setVehicleModel(nc.getClient().getVehicle().getModel());
		cd.setVehicleReg(nc.getClient().getVehicle().getVrn());

		dc.setClient(cd);
		
		ReferrerDetail rd = new ReferrerDetail();
		String ad[] = address.split(",");
		rd.setAddress1(ad[0]);
		rd.setAddress2(ad[1]);
		rd.setAddress3(ad[2]);
		rd.setPostcode(ad[3]);
		User u = ldapService.findUser(nc.getProcessInstance().getSwimlaneAssignees().get("fruHandler"));
		rd.setContactName(u.getFirstName()+" "+u.getLastName());
		rd.setEmail(u.getEmail());
		dc.setReferrer(rd);
		dc.setVehicleDriveable(nl24ProcessInstanceService.isDriveable(nc.getAccident().getVehicleDamage()));
		
		if(nc.getThirdPartyInvolved() == Boolean.TRUE) {
			x = nc.getClient().getContact().getAddress().getHouseName();
			y = "";
			if(x != null)
				y += x;
			x = nc.getClient().getContact().getAddress().getHouseNumber();
			if(x != null)
				y += x;
			ThirdPartyDetail tpd = new ThirdPartyDetail();
			tpd.setAddress1(y + " " + nc.getThirdParty().getContact().getAddress().getStreet1());
			tpd.setAddress2(nc.getThirdParty().getContact().getAddress().getStreet2());
			tpd.setAddress3(nc.getThirdParty().getContact().getAddress().getDistrict());
			tpd.setAddress4(nc.getThirdParty().getContact().getAddress().getCity());
			tpd.setPostcode(nc.getThirdParty().getContact().getAddress().getPostcode());
			dc.getThirdParty().add(tpd);
			
			int i = 1;
			for(Passenger p : nc.getClient().getPassengers()) {
				if(!p.isPopulated())
					continue;
				PassengerDetail pd = new PassengerDetail();
				pd.setPassengerNumber(i);
				i++;
				x = p.getContact().getAddress().getHouseName();
				y = "";
				if(x != null)
					y += x;
				x = p.getContact().getAddress().getHouseNumber();
				if(x != null)
					y += x;
				pd.setAddress1(y + " " + p.getContact().getAddress().getStreet1());
				pd.setAddress2(p.getContact().getAddress().getStreet2());
				pd.setAddress3(p.getContact().getAddress().getDistrict());
				pd.setAddress4(p.getContact().getAddress().getCity());
				pd.setPostcode(p.getContact().getAddress().getPostcode());
				dc.getPassenger().add(pd);
			}
			i = 1;
			for(Witness w : nc.getWitnesses()) {
				if(!w.isPopulated())
					continue;
				WitnessDetail wd = new WitnessDetail();
				wd.setWitnessNumber(i);
				i++;
				x = w.getContact().getAddress().getHouseName();
				y = "";
				if(x != null)
					y += x;
				x = w.getContact().getAddress().getHouseNumber();
				if(x != null)
					y += x;
				wd.setAddress1(y + " " + w.getContact().getAddress().getStreet1());
				wd.setAddress2(w.getContact().getAddress().getStreet2());
				wd.setAddress3(w.getContact().getAddress().getDistrict());
				wd.setAddress4(w.getContact().getAddress().getCity());
				wd.setPostcode(w.getContact().getAddress().getPostcode());
				dc.getWitness().add(wd);
			}
		}
		*/
	}
	
	GregorianCalendar gregorianCalendar = new GregorianCalendar();
	
	private synchronized XMLGregorianCalendar convDate(Date date) {
		gregorianCalendar.setTime(date);
		return dataTypeFactory.newXMLGregorianCalendar(gregorianCalendar);
	}
	
}
