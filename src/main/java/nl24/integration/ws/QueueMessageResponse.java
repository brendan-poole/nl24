package nl24.integration.ws;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.roo.addon.javabean.RooJavaBean;

@XmlType
@XmlRootElement
@RooJavaBean
public class QueueMessageResponse {
	
	private Integer status;
	private String errorMessage;
	private String errorDetails;
}
