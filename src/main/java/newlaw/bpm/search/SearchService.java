package newlaw.bpm.search;

import java.util.List;

import javax.mail.search.SearchException;

import newlaw.bpm.processinstance.ProcessInstance;

public abstract class SearchService {

	public abstract String[] getSearchTypes();
	
	public abstract List<ProcessInstance> doSearch(String type, String searchText) throws SearchException; 
}
