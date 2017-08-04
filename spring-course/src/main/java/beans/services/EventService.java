package beans.services;

import beans.dto.EventSeatsDTO;
import com.epam.models.Auditorium;
import com.epam.models.Event;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserDTO: Dmytro_Babichev
 * Date: 2/3/2016
 * Time: 11:02 AM
 */
public interface EventService {

    public Event create(Event event);

    public void remove(Event event);

    Event getEvent(String name, Auditorium auditorium, LocalDateTime dateTime);

    public List<Event> getByName(String name);

    public Event getById(long id);

    public EventSeatsDTO getSeatsAvailability(long eventId);

    public List<Event> getAll();

    public List<Event> getForDateRange(LocalDateTime from, LocalDateTime to);

    public List<Event> getNextEvents(LocalDateTime to);

    public Event assignAuditorium(Event event, Auditorium auditorium, LocalDateTime date);
}
