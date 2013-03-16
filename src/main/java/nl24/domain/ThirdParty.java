package nl24.domain;

import javax.persistence.Table;
import javax.xml.bind.annotation.XmlType;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
//@RooToString
@Table(schema="nl24")
@RooJpaActiveRecord
public class ThirdParty extends Party {

}
