package newlaw;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

// Make all ids and version property fields xml transient
public privileged aspect Xml {

	declare @method : public * *.getVersion(..) : @XmlTransient;
	declare @method : public * *.getId(..) : @XmlTransient;
}
