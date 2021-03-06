package beans.services;

import beans.daos.BookingDAO;
import beans.daos.EventDAO;
import beans.exceptions.IncufficientMoneyException;
import com.epam.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service("bookingServiceImpl")
@PropertySource({"classpath:strategies/booking.properties"})
@Transactional
public class BookingServiceImpl implements BookingService {

    private final AuditoriumService auditoriumService;
    private final UserService userService;
    private final BookingDAO bookingDAO;
    final int minSeatNumber;
    final double vipSeatPriceMultiplier;
    final double highRatedPriceMultiplier;
    final double defaultRateMultiplier;

    @Autowired
    @Qualifier("eventDAO")
    private EventDAO eventDAO;

    @Autowired
    @Qualifier("userAccountServiceImpl")
    private UserAccountService userAccountService;

    @Autowired
    public BookingServiceImpl(
                              @Qualifier("auditoriumServiceImpl") AuditoriumService auditoriumService,
                              @Qualifier("userServiceImpl") UserService userService,
                              @Qualifier("bookingDAO") BookingDAO bookingDAO,
                              @Value("${min.seat.number}") int minSeatNumber,
                              @Value("${vip.seat.price.multiplier}") double vipSeatPriceMultiplier,
                              @Value("${high.rate.price.multiplier}") double highRatedPriceMultiplier,
                              @Value("${def.rate.price.multiplier}") double defaultRateMultiplier) {
        this.auditoriumService = auditoriumService;
        this.userService = userService;
        this.bookingDAO = bookingDAO;
        this.minSeatNumber = minSeatNumber;
        this.vipSeatPriceMultiplier = vipSeatPriceMultiplier;
        this.highRatedPriceMultiplier = highRatedPriceMultiplier;
        this.defaultRateMultiplier = defaultRateMultiplier;
    }

    @Override
    public double getTicketPrice(String eventName, String auditoriumName, LocalDateTime dateTime, List<Integer> seats,
                                 User user) {
        if (Objects.isNull(eventName)) {
            throw new NullPointerException("Event name is [null]");
        }
        if (Objects.isNull(seats)) {
            throw new NullPointerException("Seats are [null]");
        }
        if (Objects.isNull(user)) {
            throw new NullPointerException("User is [null]");
        }
        if (seats.contains(null)) {
            throw new NullPointerException("Seats contain [null]");
        }

        final Auditorium auditorium = auditoriumService.getByName(auditoriumName);

        final Event event = eventDAO.get(eventName, auditorium, dateTime);
        if (Objects.isNull(event)) {
            throw new IllegalStateException(
                    "There is no event with name: [" + eventName + "] in auditorium: [" + auditorium + "] on date: ["
                            + dateTime + "]");
        }

        return getTicketPrice(event, seats);

    }

    private double getTicketPriceByEvent(Event event, List<Integer> seats) {

        final double baseSeatPrice = event.getBasePrice();
        final double rateMultiplier = event.getRate() == Rate.HIGH ? highRatedPriceMultiplier : defaultRateMultiplier;
        final double seatPrice = baseSeatPrice * rateMultiplier;
        final double vipSeatPrice = vipSeatPriceMultiplier * seatPrice;


        validateSeats(seats, event.getAuditorium());

        final List<Integer> auditoriumVipSeats = event.getAuditorium().getVipSeatsList();
        final List<Integer> vipSeats = auditoriumVipSeats.stream().filter(seats::contains).collect(
                Collectors.toList());
        final List<Integer> simpleSeats = seats.stream().filter(seat -> !vipSeats.contains(seat)).collect(
                Collectors.toList());

        final double simpleSeatsPrice = simpleSeats.size() * seatPrice;
        final double vipSeatsPrice = vipSeats.size() * vipSeatPrice;

        return simpleSeatsPrice + vipSeatPrice;
    }

    private void validateSeats(List<Integer> seats, Auditorium auditorium) {
        final int seatsNumber = auditorium.getSeatsNumber();
        final Optional<Integer> incorrectSeat = seats.stream().filter(
                seat -> seat < minSeatNumber || seat > seatsNumber).findFirst();
        incorrectSeat.ifPresent(seat -> {
            throw new IllegalArgumentException(
                    String.format("Seat: [%s] is incorrect. Auditorium: [%s] has [%s] seats", seat, auditorium.getName(),
                            seatsNumber));
        });
    }

    @Override
    @Transactional(rollbackFor = {IncufficientMoneyException.class, IllegalStateException.class, NullPointerException.class},
                isolation = Isolation.SERIALIZABLE)
    public Ticket bookTicket(User user, Ticket ticket) throws IncufficientMoneyException {
        if (Objects.isNull(user)) {
            throw new NullPointerException("User is [null]");
        }
        User foundUser = userService.getById(user.getId());
        if (Objects.isNull(foundUser)) {
            throw new IllegalStateException("User: [" + user + "] is not registered");
        }

        List<Ticket> bookedTickets = bookingDAO.getTickets(ticket.getEvent());
        boolean seatsAreAlreadyBooked = bookedTickets.stream().filter(bookedTicket -> ticket.getSeatsList().stream().filter(
                bookedTicket.getSeatsList()::contains).findAny().isPresent()).findAny().isPresent();

        if (!seatsAreAlreadyBooked && checkUserBalance(user, ticket)) {
            bookingDAO.create(user, ticket);
            userAccountService.withdrawMoney(user.getId(), ticket.getPrice());
        } else {
            throw new IllegalStateException("Unable to book ticket: [" + ticket + "]. Seats are already booked.");
        }

        return ticket;
    }

    @Override
    public List<Ticket> getTicketsForEvent(String event, String auditoriumName, LocalDateTime date) {
        final Auditorium auditorium = auditoriumService.getByName(auditoriumName);
        final Event foundEvent = eventDAO.get(event, auditorium, date);
        return bookingDAO.getTickets(foundEvent);
    }

    @Override
    public List<Ticket> getTicketsForEvent(Event event) {
        return bookingDAO.getTickets(event);
    }

    @Override
    public double getTicketPrice(Event event, List<Integer> seats) {
        return getTicketPriceByEvent(event, seats);
    }

    private boolean checkUserBalance(User user, Ticket ticket) throws IncufficientMoneyException {
        UserAccount userAccount = userAccountService.createOrGet(user.getId());
        if (userAccount == null || userAccount.getPrepaidMoney() < ticket.getPrice()) {
            throw new IncufficientMoneyException("Cannot book ticket because there is not enough money on the account");
        }
        return true;
    }

}
