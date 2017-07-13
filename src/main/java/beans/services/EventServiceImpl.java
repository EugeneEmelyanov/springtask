package beans.services;

import beans.daos.BookingDAO;
import beans.daos.EventDAO;
import beans.dto.EventSeatsDTO;
import beans.models.Auditorium;
import beans.models.Event;
import beans.models.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 2/2/2016
 * Time: 12:29 PM
 */
@Service("eventServiceImpl")
@PropertySource({"classpath:strategies/booking.properties"})
@Transactional
public class EventServiceImpl implements EventService {

    private final EventDAO eventDAO;

    private int minSeatNumber;

    @Autowired
    @Qualifier("bookingDAO")
    private BookingDAO bookingDAO;

    @Autowired
    public EventServiceImpl(@Qualifier("eventDAO") EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public Event create(Event event) {
        return eventDAO.create(event);
    }

    public void remove(Event event) {
        eventDAO.delete(event);
    }

    public List<Event> getByName(String name) {
        return eventDAO.getByName(name);
    }

    @Override
    public Event getById(long id) {
        return eventDAO.getById(id);
    }

    @Override
    public EventSeatsDTO getSeatsAvailability(long eventId) {
        Event event = getById(eventId);
        Objects.requireNonNull(event, "Cannot find event with id=" + eventId);


        List<Ticket> bookedTickets = bookingDAO.getTickets(event);


        event.getAuditorium().

        boolean seatsAreAlreadyBooked = bookedTickets.stream().filter(bookedTicket -> ticket.getSeatsList().stream().filter(
                bookedTicket.getSeatsList()::contains).findAny().isPresent()).findAny().isPresent();
    }

    public Event getEvent(String name, Auditorium auditorium, LocalDateTime dateTime) {
        return eventDAO.get(name, auditorium, dateTime);
    }

    public List<Event> getAll() {
        return eventDAO.getAll();
    }

    public List<Event> getForDateRange(LocalDateTime from, LocalDateTime to) {
        return eventDAO.getForDateRange(from, to);
    }

    public List<Event> getNextEvents(LocalDateTime to) {
        return eventDAO.getNext(to);
    }

    public Event assignAuditorium(Event event, Auditorium auditorium, LocalDateTime date) {
        final Event updatedEvent = new Event(event.getId(), event.getName(), event.getRate(), event.getBasePrice(), date, auditorium);
        return eventDAO.update(updatedEvent);
    }

    private boolean isRegularSeatsAvailable(Event event, List<Ticket> bookedTickets ) {
        HashSet<Integer> occupiedSeats = bookedTickets.stream()
                .map(ticket -> ticket.getSeatsList())
                .flatMap(seats -> seats.stream())
                .collect(Collectors.toCollection(HashSet::new));

        for(int seat = )
    }
}
