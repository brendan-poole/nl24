package newlaw.dap;

import javax.persistence.Table;

import newlaw.bpm.phase.PhaseAssignment;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@Table(name="phase_assignment", schema="dap")
@RooJpaActiveRecord
public class DapPhaseAssignment extends PhaseAssignment {

	
}
