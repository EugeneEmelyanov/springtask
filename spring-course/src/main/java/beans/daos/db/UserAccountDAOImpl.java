package beans.daos.db;

import beans.daos.AbstractDAO;
import beans.daos.UserAccountDAO;
import com.epam.models.User;
import com.epam.models.UserAccount;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by yauhen_yemelyanau on 7/17/17.
 */
@Repository(value = "userAccountDAO")
public class UserAccountDAOImpl extends AbstractDAO implements UserAccountDAO {
    @Override
    public UserAccount create(User user, Double money) {
        UserAccount userAccount = UserAccount.newBuilder()
                .withUser(user)
                .withPrepaidMoney(money)
                .build();

        Serializable save = getCurrentSession().save(userAccount);
        return userAccount;
    }

    @Override
    public UserAccount getByUserId(final long userId) {
        Query query = getCurrentSession().createQuery(
                "FROM UserAccount UA WHERE UA.user.id = :userId");
        query.setParameter("userId", userId);
        return (UserAccount) query.uniqueResult();
    }

    @Override
    public List<UserAccount> getAccountsForUsers(List<Long> userIds) {
        ;
        Query query = getCurrentSession().createQuery(
                "FROM UserAccount UA WHERE UA.user.id in(:userIds)"
        ).setParameterList("userIds", userIds);

        final List list = query.list();
        return list;
    }

    public UserAccount update(final UserAccount userAccount) {
        getCurrentSession().update(userAccount);
        return userAccount;
    }


    private void checkUserAccount(final UserAccount userAccount) {
        Objects.requireNonNull(userAccount, "User account cannot be null");
        Objects.requireNonNull(userAccount.getUser(), "User for user account cannot be null");
        if (userAccount.getPrepaidMoney() <= 0) {
            throw new IllegalArgumentException("Prepaid amount of money cannot be null");
        }
    }

}
