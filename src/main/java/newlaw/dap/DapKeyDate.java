package newlaw.dap;

import javax.persistence.Table;

import newlaw.bpm.keydate.KeyDate;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@Table(name="key_date", schema="dap")
@RooJpaActiveRecord
public class DapKeyDate extends KeyDate {

}
