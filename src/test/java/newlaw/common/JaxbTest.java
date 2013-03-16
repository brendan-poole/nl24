package newlaw;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import nl24.bpm.Nl24ProcessInstance;
import nl24.domain.Nl24Person;

public class JaxbTest {
    public static void main(String[] args) throws Exception {
 /*
    	JAXBContext jc = JAXBContext.newInstance(Nl24ProcessInstance.class);
        //Unmarshaller unmarshaller = jc.createUnmarshaller();
        //Nl24ProcessInstance customer = (Customer) unmarshaller.unmarshal(new File("input.xml"));
        
        Nl24ProcessInstance pi = new Nl24ProcessInstance();
        pi.getClient().setOccupation("Builder");
        ((Nl24Person)pi.getClient().getContact()).setForename("Brendan");
        ((Nl24Person)pi.getClient().getContact()).setSurname("Poole");
        
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(pi, System.out);
        */
    	
    	
    }
}
