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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
                .map(account -> account.getUser().getId())
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
        if (amount < 0) {
            throw new IllegalArgumentException("Amount of money cannot be less than 0");
        }

        UserAccount account = userAccountDAO.getByUserId(userId);

        if (account != null) {
            account.setPrepaidMoney(account.getPrepaidMoney() + amount);
            userAccountDAO.update(account);
        } else {
            User user = userDAO.get(userId);
            Objects.requireNonNull(user, "Cannot find user for userId: " + userId);
            userAccountDAO.create(user, amount);
        }
    }

    @Override
    public void withdrawMoney(long userId, Double amount) throws IncufficientMoneyException {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount of money cannot be less than 0");
        }

        UserAccount userAccount = userAccountDAO.getByUserId(userId);
        Objects.requireNonNull(userAccount, "Cannot find account for user " + userId);

        if (userAccount.getPrepaidMoney() < amount) {
            throw new IncufficientMoneyException("Cannot withdraw " + amount + " from user " + userId);
        }

        userAccount.setPrepaidMoney(userAccount.getPrepaidMoney() - amount);
        userAccountDAO.update(userAccount);
    }
}
