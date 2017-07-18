package beans.services;

import beans.daos.BookingDAO;
import beans.daos.UserDAO;
import beans.models.Ticket;
import beans.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 2/1/2016
 * Time: 7:30 PM
 */
@Service
@Transactional()
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final BookingDAO bookingDAO;

    @Autowired
    public UserServiceImpl(@Qualifier("userDAO") UserDAO userDAO,
                           @Qualifier("bookingDAO") BookingDAO bookingDAO) {
        this.userDAO = userDAO;
        this.bookingDAO = bookingDAO;
    }

    public User register(User user) {
        return userDAO.create(user);
    }

    public void remove(User user) {
        userDAO.delete(user);
    }

    public User getById(long id) {
        return userDAO.get(id);
    }

    public User getUserByEmail(String email) {
        return userDAO.getByEmail(email);
    }

    public List<User> getUsersByName(String name) {
        return userDAO.getAllByName(name);
    }

    public List<Ticket> getBookedTickets() {
        return bookingDAO.getAllTickets();
    }

    @Override
    public List<Ticket> getBookedTicketsByUser(long id) {

        User user = userDAO.get(id);

        Objects.requireNonNull(user, String.format("Cannot find user for id %d", id));

        return bookingDAO.getTickets(userDAO.get(id));
    }
}
