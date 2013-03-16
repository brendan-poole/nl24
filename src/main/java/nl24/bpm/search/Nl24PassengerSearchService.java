package nl24.bpm.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.search.SearchService;
import nl24.bpm.Nl24ProcessInstance;
import nl24.passenger.Nl24PassengerProcessInstance;

public class Nl24PassengerSearchService extends SearchService {

	public List<ProcessInstance> searchByClaimRef(String ref) {
		if(ref == null || ref.trim().isEmpty()) {
			return new ArrayList<ProcessInstance>();
		}
		ref = ref.replaceAll(" ", "").replaceAll("-", "").toLowerCase();
		String brokerCode = "";
		String id = "";
		if(ref.matches("^[a-z]{3,3}\\d+$")) {
			brokerCode = ref.substring(0, 3);
			id = ref.substring(3);
		} else if (ref.matches("^\\d+$")) {
			id = ref;
		} else if(ref.matches("[a-z]{3,3}")) {
			brokerCode=ref;
		}
		if(id.isEmpty())
			return Collections.emptyList();
		List<ProcessInstance> l = Nl24PassengerProcessInstance.entityManager().createNamedQuery(

				"findPassengerByClaimRef", ProcessInstance.class)
				//.setParameter("broker", "%"+brokerCode+"%")
				.setParameter("id", Long.valueOf(id))
				.getResultList();
		return l;

		
		
	}
	
	private List<ProcessInstance> searchByClientNamePattern(String name) {
		name = name.replaceAll(",", "");
		String[] names = name.trim().split(" +");
		if(names.length == 1) {
			String n = names[0];
			names = new String[2];
			names[0] = n;
			names[1] = "";
		}
			
		List<ProcessInstance> l = Nl24ProcessInstance.entityManager().createNamedQuery(

				"findPassengerByClientNamePatterns", ProcessInstance.class)
				.setParameter("name1", "%"+names[0]+"%")
				.setParameter("name2", "%"+names[1]+"%")
				.getResultList();
		return  l;
	}
	
	@Override
	public List<ProcessInstance> doSearch(String type, String searchText) {
		if(type.equals("Passenger name"))
			return this.searchByClientNamePattern(searchText);
		else if(type.equals("Passenger claim ref"))
			return this.searchByClaimRef(searchText);
		return null;
	}
	@Override
	public String[] getSearchTypes() {
		return new String[] { "Passenger claim ref", "Passenger name"};
	}
}
