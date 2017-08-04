package beans.daos.db;

import beans.daos.AbstractDAO;
import beans.daos.UserDAO;
import com.epam.models.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * UserDTO: Dmytro_Babichev
 * Date: 20/2/16
 * Time: 4:35 PM
 */
@Repository(value = "userDAO")
public class
UserDAOImpl extends AbstractDAO implements UserDAO {

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        UserDAO.validateUser(user);
        User byEmail = getByEmail(user.getEmail());
        if (Objects.nonNull(byEmail)) {
            throw new IllegalStateException(
                    String.format("Unable to store user: [%s]. UserDTO with email: [%s] is already created.", user,
                                  user.getEmail()));
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Long userId = (Long) getCurrentSession().save(user);
            return user.withId(userId);
        }
    }

    @Override
    public void delete(User user) {
        UserDAO.validateUser(user);
        getCurrentSession().delete(user);
    }

    @Override
    public User get(long id) {
        return getCurrentSession().get(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        return ((User) createBlankCriteria(User.class).add(Restrictions.eq("email", email)).uniqueResult());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllByName(String name) {
        return createBlankCriteria(User.class).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return ((List<User>) createBlankCriteria(User.class).list());
    }
}
