package beans.controllers.soap;

import com.epam.ws.model.requests.EventRequest;
import beans.services.EventService;
import com.epam.ws.model.converters.EventConverter;
import com.epam.ws.model.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint()
public class EventsEndpoint {
    private static final String NAMESPACE_URI = "http://epam.com/springcourse";

    @Autowired
    @Qualifier("eventServiceImpl")
    private EventService eventService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "eventRequest")
    @ResponsePayload()
    public EventDTO getEvents(@RequestPayload EventRequest request) {
        return EventConverter.convertFromModelToWs(eventService.getById(request.getId()));
    }

}
