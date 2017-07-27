package beans.services;

import beans.exceptions.IncufficientMoneyException;
import beans.models.UserAccount;

import java.util.List;

/**
 * Created by yauhen_yemelyanau on 7/17/17.
 */
public interface UserAccountService {

    void depositMoney(final long userId, final Double amount);

    void withdrawMoney(final long userId, final Double amount) throws IncufficientMoneyException;

    List<UserAccount> createOrGet(List<Long> userIds);

    UserAccount createOrGet(final long userId);
}
