package beans.daos;

import com.epam.models.User;

import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * UserDTO: Dmytro_Babichev
 * Date: 2/2/2016
 * Time: 11:38 AM
 */
public interface UserDAO {

    User create(User user);

    void delete(User user);

    User get(long id);

    User getByEmail(String email);

    List<User> getAllByName(String name);

    List<User> getAll();

    static void validateUser(User user) {
        if (Objects.isNull(user)) {
            throw new NullPointerException("UserDTO is [null]");
        }
        if (Objects.isNull(user.getEmail())) {
            throw new NullPointerException("UserDTO's email is [null]. UserDTO: [" + user + "]");
        }
        if (Objects.isNull(user.getName())) {
            throw new NullPointerException("UserDTO's name is [null]. UserDTO: [" + user + "]");
        }
    }
}
