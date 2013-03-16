package newlaw.bpm.search;

import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class SearchCommand {
	private String searchText;
	private String searchField;
	private String searchProcessDefinition;
	
}
