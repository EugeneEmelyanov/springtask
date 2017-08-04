package com.epam.soapclient;

import com.fasterxml.jackson.core.util.RequestPayload;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import wsdl.EventDTO;
import wsdl.EventRequest;

import java.util.List;

public class EventSOAPClient extends WebServiceGatewaySupport {

    public EventSOAPClient() {
        config();
    }

    public List<EventDTO> getEvents() {
        List<EventDTO> response = (List<EventDTO>) getWSTemplate()
                .marshalSendAndReceive(new EventRequest(),
                        new SoapActionCallback("http://localhost:8080/ws/events/getEvents"));
        return response;
    }

    private void config() {
        this.setDefaultUri("http://localhost:8080/ws/events/events.wsdl");
    }

    private WebServiceTemplate getWSTemplate() {
        WebServiceTemplate webServiceTemplate = getWebServiceTemplate();

        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(EventDTO.class, EventRequest.class);
        webServiceTemplate.setMarshaller(marshaller);
        webServiceTemplate.setUnmarshaller(marshaller);

        return webServiceTemplate;
    }

}
