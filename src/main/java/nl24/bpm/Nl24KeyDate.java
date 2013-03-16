package nl24.bpm;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Table;

import newlaw.bpm.keydate.KeyDate;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@Table(name="key_date", schema="nl24")
@RooJpaActiveRecord
@DiscriminatorValue("nl24")
public class Nl24KeyDate extends KeyDate {

}
