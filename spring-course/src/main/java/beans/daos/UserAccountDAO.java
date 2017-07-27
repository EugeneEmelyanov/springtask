package beans.daos;

import beans.models.User;
import beans.models.UserAccount;

import java.util.List;

/**
 * Created by yauhen_yemelyanau on 7/15/17.
 */
public interface UserAccountDAO {

    UserAccount create(User user, Double money);

    UserAccount getByUserId(final long userId);

    UserAccount update(UserAccount userAccount);

    List<UserAccount> getAccountsForUsers(List<Long> userIds);


}
