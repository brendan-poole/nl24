package nl24.integration;

import javax.xml.bind.JAXBException;

public class IntegrationException extends RuntimeException {

	public IntegrationException(Exception e) {
		super(e);
	}

}
