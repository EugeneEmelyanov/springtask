package beans.services;

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
import java.util.*;
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
    @Qualifier("bookingServiceImpl")
    private BookingService bookingService;

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


        List<Ticket> bookedTickets = bookingService.getTicketsForEvent(event);


        return addVipSeatsAvailability(event, bookedTickets,
                addRegularSeatsAvailability(event, bookedTickets, EventSeatsDTO.newBuilder()
                .withEvent(event))).build();

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

    private EventSeatsDTO.Builder addRegularSeatsAvailability(Event event, List<Ticket> bookedTickets, EventSeatsDTO.Builder builder) {
        HashSet<Integer> occupiedSeats = bookedTickets.stream()
                .map(ticket -> ticket.getSeatsList())
                .flatMap(seats -> seats.stream())
                .collect(Collectors.toCollection(HashSet::new));

        int numSeats = 0;
        int firstAvailable = -1;
        List<Integer> seats = new LinkedList<>();
        for (int seat = minSeatNumber+1; seat < event.getAuditorium().getSeatsNumber(); seat++) {
            if (!occupiedSeats.contains(seat)) {
                numSeats += 1;
                firstAvailable = seat;
                seats.add(seat);
            }
        }

        double regularPrice = Double.NEGATIVE_INFINITY;

        if (firstAvailable != -1) {
            regularPrice = bookingService.getTicketPrice(event, Arrays.asList(firstAvailable));
        }


        builder.withRegularPrice(regularPrice)
                .withNumRegularSeats(numSeats)
                .withRegularSeats(seats);

        return builder;
    }

    private EventSeatsDTO.Builder addVipSeatsAvailability(Event event, List<Ticket> bookedTickets, EventSeatsDTO.Builder builder) {
        HashSet<Integer> occupiedSeats = bookedTickets.stream()
                .map(ticket -> ticket.getSeatsList())
                .flatMap(seats -> seats.stream())
                .collect(Collectors.toCollection(HashSet::new));

        List<Integer> seats = event.getAuditorium().getVipSeatsList()
                .stream()
                .filter(i -> !occupiedSeats.contains(i))
                .collect(Collectors.toList());

        int vipSeat = seats.isEmpty() ? -1 : seats.get(0);
        double vipSeatPrice = Double.NEGATIVE_INFINITY;

        if (vipSeat != -1) {
            vipSeatPrice = bookingService.getTicketPrice(event, Arrays.asList(vipSeat));
        }

        builder.withVipPrice(vipSeatPrice)
                .withNumVipSeats(seats.size())
                .withVipSeats(seats);

        return builder;
    }
}
