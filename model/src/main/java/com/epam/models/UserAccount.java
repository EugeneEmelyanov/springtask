package com.epam.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by yauhen_yemelyanau on 7/15/17.
 */
public class UserAccount {



    private long id;
    //ideally we should use BigDecimal, however, Double is used for simplicity
    private Double prepaidMoney;
    private User user;

    public UserAccount() {
    }

    private UserAccount(Builder builder) {
        setId(builder.id);
        setPrepaidMoney(builder.prepaidMoney);
        setUser(builder.user);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getPrepaidMoney() {

        return prepaidMoney;
    }

    public void setPrepaidMoney(Double prepaidMoney) {
        this.prepaidMoney = prepaidMoney;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "prepaidMoney=" + prepaidMoney +
                ", user=" + user +
                '}';
    }


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        return new EqualsBuilder()
                .append(prepaidMoney, that.prepaidMoney)
                .append(user, that.user)
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(1,43)
                .append(prepaidMoney)
                .append(user)
                .append(id)
                .toHashCode();
    }

    public static final class Builder {
        private Double prepaidMoney;
        private User user;
        private long id;

        private Builder() {
        }

        public Builder withPrepaidMoney(Double val) {
            prepaidMoney = val;
            return this;
        }

        public Builder withUser(User val) {
            user = val;
            return this;
        }

        public UserAccount build() {
            return new UserAccount(this);
        }

        public Builder withId(long val) {
            id = val;
            return this;
        }
    }
}
