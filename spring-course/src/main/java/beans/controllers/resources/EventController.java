package beans.controllers.resources;

import com.epam.models.Event;
import beans.services.AuditoriumService;
import beans.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * Created by yauhen_yemelyanau on 7/26/17.
 */
@RestController("eventResourceController")
@RequestMapping("api/v1/events")
public class EventController {

    @Autowired
    @Qualifier("eventServiceImpl")
    private EventService eventService;

    @Autowired
    @Qualifier("auditoriumServiceImpl")
    private AuditoriumService auditoriumService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Event> getAllEvents() {
        return eventService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = {"application/json", "application/pdf"})
    public Event createEvent(@RequestBody Event event) {
        Objects.requireNonNull(event, "Event cannot be null");
        Objects.requireNonNull(event.getBasePrice(), "Price cannot be null");
        Objects.requireNonNull(event.getDateTime(), "Date cannot be null");
        Objects.requireNonNull(event.getName(), "Name cannot be null");
        Objects.requireNonNull(event.getAuditorium(), "Auditorium cannot be null");
        Objects.requireNonNull(event.getRate(), "Rate cannot be null");

        Event createdEvent = eventService.create(event);
        //TODO:create getById method in DAO/Service
        createdEvent.setAuditorium(auditoriumService.getAuditoriums().stream()
                .filter(auditorium -> auditorium.getId() == createdEvent.getAuditorium().getId())
                .findFirst().get());
        return createdEvent;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET, produces = {"application/json", "application/pdf"})
    public Event getEvent(@PathVariable("id") long id) {
        return eventService.getById(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = {"application/json", "application/pdf"})
    public void deleteEvent(@PathVariable("id") long id, HttpServletResponse response) {
        eventService.remove(eventService.getById(id));
        response.setStatus(HttpStatus.OK.value());
    }
}
