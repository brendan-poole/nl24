package newlaw.bpm.transition;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import newlaw.bpm.handler.DueDateHandler;
import newlaw.bpm.phase.Phase;

import org.joda.time.Period;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class Transition {
	
	private String code;
	
	private String name;
	
	private Boolean email;
	
	private Phase phase;
	
	private List<Phase> toPhase = new ArrayList<Phase>();

	private String toPhaseCode;
	
	private String period;

	private String invalidatePhases;
	
	private String killPhases;
	
	private List<Period> periods = new ArrayList<Period>();
	
	private List<DueDateHandler> dueDateHandlers = new ArrayList<DueDateHandler>();
	
	@XmlTransient
	public Phase getPhase() {
		return this.phase;
	}
	
	public String toString() {
		return code;
	}
	
	@XmlTransient
	public List<Period> getPeriods() {
		return this.periods;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.code.equals(((Transition)o).getCode());
		
	}
}

