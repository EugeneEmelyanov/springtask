package beans.daos;

import com.epam.models.Event;
import com.epam.models.Ticket;
import com.epam.models.User;

import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * UserDTO: Dmytro_Babichev
 * Date: 2/4/2016
 * Time: 10:21 AM
 */
public interface BookingDAO {

    Ticket create(User user, Ticket ticket);

    void delete(User user, Ticket booking);

    List<Ticket> getTickets(Event event);

    List<Ticket> getTickets(User user);

    long countTickets(User user);

    List<Ticket> getAllTickets();

    static void validateUser(User user) {
        if (Objects.isNull(user)) {
            throw new NullPointerException("UserDTO is [null]");
        }
        if (Objects.isNull(user.getEmail())) {
            throw new NullPointerException("UserDTO email is [null]");
        }
    }

    static void validateTicket(Ticket ticket) {
        if (Objects.isNull(ticket)) {
            throw new NullPointerException("Ticket is [null]");
        }
    }
}
