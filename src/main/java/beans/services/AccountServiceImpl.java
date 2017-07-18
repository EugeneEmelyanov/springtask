package beans.services;

import beans.daos.UserAccountDAO;
import beans.daos.UserDAO;
import beans.exceptions.IncufficientMoneyException;
import beans.models.User;
import beans.models.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yauhen_yemelyanau on 7/17/17.
 */
@Service("userAccountServiceImpl")
@Transactional()
public class AccountServiceImpl implements UserAccountService {


    @Autowired
    @Qualifier("userAccountDAO")
    private UserAccountDAO userAccountDAO;

    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;


    @Override
    public UserAccount createOrGet(long userId) {
        UserAccount userAccount = userAccountDAO.getByUserId(userId);

        if (userAccount == null) {
            User user = userDAO.get(userId);
            Objects.requireNonNull(user, "Cannot find user with id " + userId);
            userAccount = userAccountDAO.create(user, 0d);
        }
        return userAccount;
    }

    @Override
    public List<UserAccount> createOrGet(final List<Long> userIds) {

        List<UserAccount> accounts = Collections.synchronizedList(userAccountDAO.getAccountsForUsers(userIds));
        Set<Long> set = (accounts.stream()
                .map(account->account.getUser().getId())
                .collect(Collectors.toSet()));

        userIds.stream()
                .filter(id -> !set.contains(id))
                .forEach(id -> {
                    accounts.add(userAccountDAO.create(userDAO.get(id), 0d));
                });
        return accounts;
    }

    @Override
    public void depositMoney(long userId, Double amount) {
        UserAccount account = userAccountDAO.getByUserId(userId);

        if (account != null) {
            userAccountDAO.add(userId, amount);
        } else {
            User user = userDAO.get(userId);
            Objects.requireNonNull(user, "Cannot find user for userId: " + userId);
            userAccountDAO.create(user, amount);
        }
    }

    @Override
    public void withdrawMoney(long userId, Double amount) throws IncufficientMoneyException{
        userAccountDAO.withdraw(userId, amount);
    }
}
