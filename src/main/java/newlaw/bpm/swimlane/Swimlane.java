package newlaw.bpm.swimlane;

import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * 
 */
@RooJavaBean
public class Swimlane {

	/** The code. */
	private String code;
	
	/** The name. */
	private String name;
	
	/** The roles. */
	private String roles;
		
	/** The assignee property. */
	private String assigneeProperty;
	
	/**
	 * Instantiates a new swimlane.
	 */
	public Swimlane() { }
	
	/**
	 * Instantiates a new swimlane.
	 *
	 * @param userGroups the user groups
	 */
	public Swimlane(String userGroups) {
		this.roles=userGroups;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return code;
	}
	

}
