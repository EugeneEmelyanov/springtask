package com.epam.restclient;

import com.epam.models.Event;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yauhen_yemelyanau on 7/28/17.
 */
public class EventResourceRestClient {

    private RestTemplate restTemplate = new RestTemplate();

    public List<Event> getAllEvents() {
        Event[] events = new Event[0];
        try {
            events = restTemplate.getForObject(getAllEventURI(), Event[].class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return Arrays.asList(events);
    }

    public Event create(Event event) {
        Event createdEvent = null;
        try {
            createdEvent = restTemplate.postForObject(getAllEventURI().toString(), event, Event.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return createdEvent;
    }

    public Event getById(final long eventId) {

        Event event = null;
        try {
            event = restTemplate.getForObject(getEventIdUrl(eventId), Event.class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return event;
    }

    public boolean removeEvent(final long eventId) {
        try {
            restTemplate.delete(getEventIdUrl(eventId));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return true;
    }

    protected URI getAllEventURI() throws URISyntaxException {
        return getBaseUriBuilder()
                .build();
    }

    protected URI getEventIdUrl(final long eventId) throws URISyntaxException {
        return getBaseUriBuilder()
                .withQuery("/api/v1/events/"+eventId+".json")
                .build();
    }

    protected URIBuilder getBaseUriBuilder() {
        return URIBuilder.get()
                .withHost("localhost")
                .withPort(8080)
                .withQuery("/api/v1/events.json");
    }

}
