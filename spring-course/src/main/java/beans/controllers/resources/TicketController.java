package beans.controllers.resources;

import com.epam.models.Ticket;
import beans.services.BookingService;
import beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by yauhen_yemelyanau on 7/19/17.
 */
@RestController("ticketResourceController")
@RequestMapping("/api/v1/tickets")
public class TicketController  {

    @Autowired
    @Qualifier("bookingServiceImpl")
    private BookingService bookingService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/pdf"})
    public List<Ticket> getTickets(@RequestParam(value = "userId", required = false)Long userId) {

        List<Ticket> tickets;

        if (userId != null) {
            tickets = userService.getBookedTicketsByUser(userId);
        } else {
            tickets = userService.getBookedTickets();
        }
        return tickets;
    }

}
