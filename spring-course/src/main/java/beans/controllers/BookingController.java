package beans.controllers;

import beans.exceptions.IncufficientMoneyException;
import com.epam.models.Auditorium;
import com.epam.models.Event;
import com.epam.models.Ticket;
import com.epam.models.User;
import beans.services.AuditoriumService;
import beans.services.BookingService;
import beans.services.EventService;
import beans.services.UserService;
import com.epam.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created by Yauhen_Yemelyanau on 7/3/2017.
 */
@Controller()
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    @Qualifier("bookingServiceImpl")
    private BookingService bookingService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("eventServiceImpl")
    private EventService eventService;

    @Autowired
    @Qualifier("auditoriumServiceImpl")
    private AuditoriumService auditoriumService;

    @RequestMapping("/ticket-price")
    public void getTicketPrice(@RequestParam String eventName,
                               @RequestParam String auditoriumName,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventDate,
                               @RequestParam List<Integer> seats,
                               @RequestParam String userEmail,
                               HttpServletResponse response) throws IOException {

        validateParams(eventName, auditoriumName, eventDate);

        if (Objects.isNull(seats) || seats.isEmpty()) {
            throw new IllegalArgumentException("seats cannot be empty");
        }

        if (Objects.isNull(userEmail)) {
            throw new IllegalArgumentException("user cannot be empty");
        }

        User u = userService.getUserByEmail(userEmail);

        if (Objects.isNull(u)) {
            throw new IllegalArgumentException("Cannot find user for id =" + userEmail);
        }

        response.getOutputStream().println(String.format("Ticket price is % ,.2f",
                bookingService.getTicketPrice(eventName, auditoriumName, eventDate, seats, u)));
        response.getOutputStream().close();

    }


    @RequestMapping("/book")
    public void bookTicket(@RequestParam String eventName,
                           @RequestParam String auditoriumName,
                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventDate,
                           @RequestParam List<Integer> seats,
                           Authentication authentication,
                           HttpServletResponse response) throws IOException, IncufficientMoneyException {

        validateParams(eventName, auditoriumName, eventDate);

        if (Objects.isNull(seats) || seats.isEmpty()) {
            throw new IllegalArgumentException("seats cannot be empty");
        }

        String userEmail = authentication.getName();

        User user = userService.getUserByEmail(userEmail);

        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("Cannot find user for id =" + userEmail);
        }

        Auditorium auditorium = auditoriumService.getByName(auditoriumName);

        if (Objects.isNull(auditorium)) {
            throw new IllegalArgumentException("Cannot find auditorium for name =" + auditoriumName);
        }

        Event event = eventService.getEvent(eventName, auditorium, eventDate);


        Ticket t = bookingService.bookTicket(user, new Ticket(event, seats, user,
                bookingService.getTicketPrice(event.getName(),
                        event.getAuditorium().getName(),
                        event.getDateTime(), seats,
                        user)));


        response.sendRedirect("/ticket/" + user.getId());
    }

    @RequestMapping("/tickets")
    public void getTicketsForEvent(@RequestParam String eventName,
                                   @RequestParam String auditoriumName,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventDate,
                                   HttpServletResponse response) throws IOException {

        validateParams(eventName, auditoriumName, eventDate);


        List<Ticket> tickets = bookingService.getTicketsForEvent(eventName, auditoriumName, eventDate);

        ServletOutputStream out = response.getOutputStream();
        out.println(String.format("Here are the tickets available : %d", tickets.size()));
        tickets.stream()
                .forEach(ticket -> {
                    try {
                        out.println(ticket.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        out.close();
    }

    private void validateParams(String event, String auditorium, LocalDateTime date) {
        if (Objects.isNull(event)) {
            throw new IllegalArgumentException("event cannot be empty");
        }

        if (Objects.isNull(auditorium)) {
            throw new IllegalArgumentException("auditorium cannot be empty");
        }

        if (Objects.isNull(date)) {
            throw new IllegalArgumentException("date cannot be empty");
        }
    }
}
