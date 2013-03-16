package nl24.domain;

import javax.xml.bind.annotation.XmlType;

import newlaw.domain.Person;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
//@RooToString
// Class needs to be named differently so it doesn't conflict with the superclass but table name can be the same.
@RooJpaActiveRecord(table="person", schema = "nl24")
public class Nl24Person extends Person {

}
