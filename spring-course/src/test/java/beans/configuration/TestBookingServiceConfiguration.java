package beans.configuration;

import beans.daos.AuditoriumDAO;
import beans.daos.BookingDAO;
import beans.daos.EventDAO;
import beans.daos.UserDAO;
import beans.daos.mocks.BookingDAOBookingMock;
import beans.daos.mocks.DBAuditoriumDAOMock;
import beans.daos.mocks.EventDAOMock;
import beans.daos.mocks.UserDAOMock;
import beans.exceptions.IncufficientMoneyException;
import com.epam.models.*;
import com.epam.security.Roles;
import beans.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.Mockito.*;


import java.time.LocalDate;
import java.util.*;


@Configuration()
public class TestBookingServiceConfiguration {

    @Bean
    public BookingDAO bookingBookingDAO() {
        HashSet<Ticket> tickets = new HashSet<Ticket>() {
            {
                addAll(tickets());
            }
        };
        return new BookingDAOBookingMock(new HashMap<User, Set<Ticket>>() {
            {
                put(testUser1(), tickets);
            }
        });
    }

    @Bean(name="eventDAO")
    public EventDAO eventDAOMock() {
        return new EventDAOMock(Arrays.asList(testEvent1(), testEvent2()));
    }

    @Bean(name="eventServiceImpl")
    public EventService eventServiceImpl() {
        EventServiceImpl service =  new EventServiceImpl(eventDAOMock());
        service.setBookingService(bookingServiceImpl());
        return service;
    }

    @Bean
    public Event testEvent1() {
        return new Event(1, "Test event", Rate.HIGH, 124.0, java.time.LocalDateTime.of(2016, 2, 6, 14, 45, 0),
                testHall1());
    }

    @Bean
    public Event testEvent2() {
        return new Event(2, "Test event2", Rate.MID, 500.0, java.time.LocalDateTime.of(2016, 12, 6, 9, 35, 0),
                testHall2());
    }


    @Bean
    public User testUser1() {

        return User.newBuilder()
                .withId(0l)
                .withName("Eugene Yemelyanau")
                .withEmail("a@a.co")
                .withUserRoles(Roles.REGISTERED_USER.getAuthority())
                .withPassword("123")
                .withBirthday(LocalDate.of(1988, 4, 29)).build();
    }

    @Bean
    public User testUser2() {
        return User.newBuilder()
                .withId(1l)
                .withName("Arkady Dobkin")
                .withEmail("b@b.co")
                .withUserRoles(String.join(",", new String[]{Roles.BOOKING_MANAGER.getAuthority(), Roles.REGISTERED_USER.getAuthority()}))
                .withPassword("123")
                .withBirthday(LocalDate.of(1962, 4, 29)).build();
    }

    @Bean
    public Ticket testTicket1() {
        return new Ticket(1, testEvent1(), Arrays.asList(3, 4),
                testUser1(), 32D);
    }

    @Bean
    public Ticket testTicket2() {
        return new Ticket(2, testEvent2(), Arrays.asList(1, 2),
                testUser1(), 123D);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public List<Ticket> tickets() {
        return Arrays.asList(testTicket1(), testTicket2());
    }

    @Bean
    public Auditorium testHall1() {
        return new Auditorium(1, "Test auditorium", 15, Arrays.asList(1, 2, 3, 4, 5));
    }

    @Bean
    public Auditorium testHall2() {
        return new Auditorium(2, "Test auditorium 2", 8, Collections.singletonList(1));
    }

    @Bean
    public AuditoriumDAO auditoriumDAO() {
        return new DBAuditoriumDAOMock(Arrays.asList(testHall1(), testHall2()));
    }

    @Bean
    public AuditoriumService auditoriumServiceImpl() {
        return new AuditoriumServiceImpl(auditoriumDAO());
    }


    @Bean
    public UserDAO userDAOMock() {
        return new UserDAOMock(Arrays.asList(testUser1()));
    }

    @Bean(name = "userAccountServiceImpl")
    public UserAccountService getUserAccountService() throws IncufficientMoneyException {
        UserAccountService uas = mock(UserAccountService.class);
        UserAccount account = UserAccount.newBuilder().withPrepaidMoney(1000000d).build();

        when(uas.createOrGet(anyLong())).thenReturn(account);
        return uas;
    }

    @Bean
    public UserService userServiceImpl() {
        return new UserServiceImpl(userDAOMock(), bookingBookingDAO());
    }

    @Bean(name = "testBookingServiceImpl")
    public BookingService bookingServiceImpl() {
        return new BookingServiceImpl(auditoriumServiceImpl(), userServiceImpl(),
                bookingBookingDAO(), 1, 2, 1.2, 1);
    }
}
