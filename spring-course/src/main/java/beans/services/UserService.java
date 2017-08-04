package beans.services;

import com.epam.models.Ticket;
import com.epam.models.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * UserDTO: Dmytro_Babichev
 * Date: 2/1/2016
 * Time: 7:32 PM
 */
public interface UserService {

    User register(User user);

    void remove(User user);

    User getById(long id);

    User getUserByEmail(String email);

    List<User> getUsersByName(String name);

    List<Ticket> getBookedTickets();

    List<Ticket> getBookedTicketsByUser(long id);
}
