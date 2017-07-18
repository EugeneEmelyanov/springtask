package beans.daos;

import beans.exceptions.IncufficientMoneyException;
import beans.models.User;
import beans.models.UserAccount;

import java.util.List;

/**
 * Created by yauhen_yemelyanau on 7/15/17.
 */
public interface UserAccountDAO {

    UserAccount create(User user, Double money);

    UserAccount getByUserId(final long userId);

    void add(final long userId, final double money);

    void withdraw(final long userId, final double money) throws IncufficientMoneyException;

    List<UserAccount> getAccountsForUsers(List<Long> userIds);


}
