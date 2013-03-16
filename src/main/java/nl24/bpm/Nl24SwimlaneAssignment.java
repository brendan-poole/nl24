package nl24.bpm;

import javax.persistence.Table;

import newlaw.bpm.swimlane.SwimlaneAssignment;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@Table(name="swimlane_assignment", schema="nl24")
@RooJpaActiveRecord
public class Nl24SwimlaneAssignment extends SwimlaneAssignment {

}
