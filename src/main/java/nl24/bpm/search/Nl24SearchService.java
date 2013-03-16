package nl24.bpm.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.mail.search.SearchException;

import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.bpm.search.SearchService;
import nl24.bpm.Nl24ProcessInstance;

public class Nl24SearchService extends SearchService {

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
		List<ProcessInstance> l = Nl24ProcessInstance.entityManager().createNamedQuery(

				"findByClaimRef", ProcessInstance.class)
				.setParameter("broker", "%"+brokerCode+"%")
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

				"findByClientNamePatterns", ProcessInstance.class)
				.setParameter("name1", "%"+names[0]+"%")
				.setParameter("name2", "%"+names[1]+"%")
				.getResultList();
		return  l;
		
	}
	
	@Override
	public List<ProcessInstance> doSearch(String type, String searchText) throws SearchException {
		// TODO: low - change search types to enums
		if(type.equals("Client name"))
			return this.searchByClientNamePattern(searchText);
		else if(type.equals("Claim ref"))
			return this.searchByClaimRef(searchText);
		throw new SearchException("Unknown type: "+type);
	}
	@Override
	public String[] getSearchTypes() {
		return new String[] { "Claim ref", "Client name"};
	}
}
