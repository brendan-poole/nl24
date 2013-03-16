package nl24.integration.ws;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.roo.addon.javabean.RooJavaBean;

@XmlType
@XmlRootElement
@RooJavaBean
public class QueueMessageRequest {
	
	private String govGatewayMessage;
	private String callerId;

	@XmlAttribute(required=true)
	public String getGovGatewayMessage() {
		return govGatewayMessage;

	}
	
	@XmlAttribute(required=true)
	public String getCallerId() {
		return callerId;

	}
	
	
}
