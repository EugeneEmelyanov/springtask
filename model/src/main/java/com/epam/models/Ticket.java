package com.epam.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.epam.util.CsvUtil;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Dmytro_Babichev
 * Date: 2/1/2016
 * Time: 7:37 PM
 */
public class Ticket {

    private long id;
    private Event event;
    private String seats;
    private User user;
    private Double price;

    public Ticket() {
    }

    public Ticket(Event event, List<Integer> seats, User user, double price) {
        this(-1, event, seats, user, price);
    }

    public Ticket(long id, Event event, List<Integer> seats, User user, Double price) {
        this(id, event, CsvUtil.fromListToCsv(seats), user, price);
    }

    public Ticket(long id, Event event, String seats, User user, Double price) {
        this.id = id;
        this.event = event;
        this.user = user;
        this.price = price;
        this.seats = seats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public void setSeatsList(List<Integer> seats) {
        this.seats = CsvUtil.fromListToCsv(seats);
    }

    public List<Integer> getSeatsList() {
        return CsvUtil.fromCsvToList(seats, Integer::valueOf);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Ticket))
            return false;

        Ticket ticket = (Ticket) o;

        return new EqualsBuilder()
                .append(event, ticket.event)
                .append(seats, ticket.seats)
                .append(user, ticket.user)
                .append(price, ticket.price)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 17)
                .append(event)
                .append(seats)
                .append(user)
                .append(price)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(event)
                .append(seats)
                .append(user)
                .append(price)
                .toString();
    }

    public Ticket withId(Long ticketId) {
        return new Ticket(ticketId, event, seats, user, price);
    }
}
