package newlaw.bpm.phase;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.Size;

import newlaw.bpm.processinstance.ProcessInstance;
import newlaw.domain.DevData;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@RooJavaBean
public class PhaseCommand {

	private ProcessInstance processInstance;
	
	private String currentPhase;
	
	private String currentPage;

	private String currentSubPage;
	
	private Object currentListItem;

	@Size(max=5)
	private String test;
	
	private List<String> messages = new ArrayList<String>();

	private DevData devComments = new DevData();
	
	//private BindingResult result;
	
	private String searchText;
	
	private int searchSeq;

	//Stores a list of the search sequence that should show the forms rather than the search field
	private String addEntry = "";
	
	@SuppressWarnings("rawtypes")
	private List searchResults;
	
	private String fileCode;
	
	private CommonsMultipartFile fileData;
	
	private String outcome;
	
	private Boolean reviewMode;
	
	private String taskForm;
	
}
