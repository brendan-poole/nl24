package newlaw.bpm.processdefinition;

import java.util.Hashtable;
import java.util.Set;
import java.util.StringTokenizer;

import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.phase.Phase;
import newlaw.bpm.processinstance.ProcessInstanceService;
import newlaw.bpm.report.ReportService;
import newlaw.bpm.search.SearchService;
import newlaw.bpm.swimlane.Swimlane;
import newlaw.bpm.transition.Transition;
import newlaw.util.AppUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Period;
import org.springframework.roo.addon.javabean.RooJavaBean;


// TODO: Auto-generated Javadoc
/**
 * The Class ProcessDefinition.
 */
@RooJavaBean
public class ProcessDefinition {

	/** The log. */
	protected Log log = LogFactory.getLog(this.getClass());

	/** The name. */
	private String name;
	
	/** The swimlanes. */
	private Set<Swimlane> swimlanes;
	
	/** The report service. */
	private ReportService reportService; 
	
	/** The swimlanes by code. */
	private Hashtable<String, Swimlane> swimlanesByCode;
	
	/**
	 * Gets the swimlanes by code.
	 *
	 * @return the swimlanes by code
	 */
	private Hashtable<String, Swimlane> getSwimlanesByCode() {
		return swimlanesByCode;
	}

	/**
	 * Sets the swimlanes by code.
	 *
	 * @param swimlanesByCode the swimlanes by code
	 */
	private void setSwimlanesByCode(Hashtable<String, Swimlane> swimlanesByCode) {
		this.swimlanesByCode = swimlanesByCode;
	}

	/** The default action handler. */
	private ActionHandler defaultActionHandler;
	
	/** The code. */
	private String code;

	/** The service. */
	private ProcessInstanceService service;
	
	/** The search service. */
	private SearchService searchService;

	//private Map<String, Transition> transitions;

	/** The phases. */
	private Set<Phase> phases;
	
	/** The phases by code. */
	private Hashtable<String, Phase> phasesByCode;

	/**
	 * Gets the phases by code.
	 *
	 * @return the phases by code
	 */
	private Hashtable<String, Phase> getPhasesByCode() {
		return phasesByCode;
	}

	/**
	 * Sets the phases by code.
	 *
	 * @param phasesByCode the phases by code
	 */
	private void setPhasesByCode(Hashtable<String, Phase> phasesByCode) {
		this.phasesByCode = phasesByCode;
	}

	/**
	 * Gets the swimlane.
	 *
	 * @param swimlane the swimlane
	 * @return the swimlane
	 */
	public Swimlane getSwimlane(String swimlane) {
		if(swimlanesByCode == null)
			throw new ProcessDefinitionException("swimlaneByCode not initialized");
		else 
			if(!swimlanesByCode.containsKey(swimlane))
				throw new IllegalArgumentException("Swimlane does not exist with code: "+swimlane);
			else
				return swimlanesByCode.get(swimlane);
	}
	
	/**
	 * Gets the phase.
	 *
	 * @param phase the phase
	 * @return the phase
	 */
	public Phase getPhase(String phase) {
		if(phasesByCode == null)
			throw new ProcessDefinitionException("phasesByCode not initialized");
		else 
			if(!phasesByCode.containsKey(phase))
				throw new IllegalArgumentException("Phase does not exist with code: "+phase);
			else
				return phasesByCode.get(phase);
	}
	
	/**
	 * Initialise the process definition 
	 */
	public void init() {
		swimlanesByCode = new Hashtable<String, Swimlane>();
		for(Swimlane s : swimlanes) 
			swimlanesByCode.put(s.getCode(), s);
		if(!swimlanesByCode.containsKey("initiator")) {
			Swimlane s = new Swimlane();
			s.setCode("initiator");
			s.setName("Initiator");
			s.setRoles("");
			swimlanesByCode.put("initiator", s);
			swimlanes.add(s);
		}

		phasesByCode = new Hashtable<String, Phase>();
		for(Phase p : phases) {
			phasesByCode.put(p.getCode(), p);
		}
		
		for(Phase p : phases) {
			log.debug(p.getCode()+" - "+p.getName()+" - ["+p.getSwimlaneCode()+"]");
			log.debug("====================================");

			p.setProcessDefinition(this);
			if(p.getSwimlaneCode() == null || p.getSwimlaneCode().isEmpty())
				p.setSwimlane(getSwimlane("initiator"));
			else
				p.setSwimlane(getSwimlane(p.getSwimlaneCode()));
				
			if(p.getTransitions() != null) {
				for(Transition t : p.getTransitions()) {
					log.debug(t.getCode()+" - "+t.getName());
					t.setPhase(p);
					if(t.getToPhaseCode() != null && !t.getToPhaseCode().isEmpty()) {
						String[] phaseCodes = t.getToPhaseCode().split(","); 
						for(String pc : phaseCodes)
							t.getToPhase().add(this.getPhase(pc));
					}
					if(t.getPeriod() != null && !t.getToPhaseCode().isEmpty()) {
						StringTokenizer st = new StringTokenizer(t.getPeriod(),",");
							while(st.hasMoreTokens()) {
								String pe = st.nextToken().trim();
								if(pe.trim().equals("now"))
									t.getPeriods().add(new Period(0,0,0,0));
								else
									t.getPeriods().add((Period) AppUtils.getApplicationContext().getBean(pe.trim()));
							}
					}
					if(t.getPeriods().size() > 0 && t.getPeriods().size() != t.getToPhase().size())
						throw new ProcessDefinitionException("Mismatch between size of to-phases and periods: "+t.getPhase());
				}
			}
			if(p.getActionHandler() == null) {
				p.setActionHandler(this.getDefaultActionHandler());
			}
			p.getActionHandler().setPhase(p);
			log.debug(" ");
		}
		if(!phasesByCode.containsKey("start")) {
			throw new ProcessDefinitionException("A phase with code 'start' must be defined.");
		}

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
