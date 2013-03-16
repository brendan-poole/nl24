package nl24.domain;

import newlaw.domain.Organisation;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
//@RooToString
@RooJpaActiveRecord(schema="nl24")
public class Insurer extends Organisation {

}
