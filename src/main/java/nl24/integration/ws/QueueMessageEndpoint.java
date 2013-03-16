/*
 * Copyright 2005-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl24.integration.ws;

import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import newlaw.bpm.keydate.KeyDateService;
import newlaw.bpm.processinstance.ProcessInstance;
import nl24.bpm.Nl24ProcessInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Element;


@Endpoint
public class QueueMessageEndpoint {

	@Autowired
	KeyDateService keyDateService;
	
    public static final String NAMESPACE_URI = "http://govgateway.lv.com";

    public static final String REQUEST_LOCAL_NAME = "queueMessageRequest";

    public static final String RESPONSE_LOCAL_NAME = "queueMessageResponse";

    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    @PayloadRoot(localPart = REQUEST_LOCAL_NAME, namespace = NAMESPACE_URI)
    @ResponsePayload
    public QueueMessageResponse handleQueueMessageRequest(@RequestPayload Element requestElement) throws JAXBException, ParserConfigurationException {
        Assert.isTrue(NAMESPACE_URI.equals(requestElement.getNamespaceURI()), "Invalid namespace");
        Assert.isTrue(REQUEST_LOCAL_NAME.equals(requestElement.getLocalName()), "Invalid local name");

        String ref = requestElement.getAttribute("reference");
        Long l;
        if(ref.matches("^[A-Za-z]{3,3}\\d+"))
        	l = Long.valueOf(ref.substring(3));
        else 
        	l = Long.valueOf(ref);
        
        Nl24ProcessInstance pi = (Nl24ProcessInstance) ProcessInstance.findProcessInstance(l);
        keyDateService.insertKeyDate(pi, "receivedByNewlaw", "complete", new Date(), "newlaw");
        
        QueueMessageResponse res = new QueueMessageResponse();
        //res.setMessage("OK");
        return res;
    }
}
