package newlaw.dap;

import javax.persistence.Table;

import newlaw.bpm.swimlane.SwimlaneAssignment;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@Table(name="swimlane_assignment", schema="dap")
@RooJpaActiveRecord
public class DapSwimlaneAssignment extends SwimlaneAssignment {

}
