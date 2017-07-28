package beans.services;

import beans.exceptions.IncufficientMoneyException;
import com.epam.models.Event;
import com.epam.models.Ticket;
import com.epam.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    double getTicketPrice(String event, String auditorium, LocalDateTime dateTime, List<Integer> seats, User user);

    Ticket bookTicket(User user, Ticket ticket) throws IncufficientMoneyException;

    List<Ticket> getTicketsForEvent(String event, String auditorium, LocalDateTime date);

    List<Ticket> getTicketsForEvent(Event event);

    double getTicketPrice(Event event, List<Integer> seats);

}
