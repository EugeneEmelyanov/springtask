package beans.configuration;

import beans.daos.BookingDAO;
import beans.daos.mocks.BookingDAOBookingMock;
import beans.daos.mocks.UserDAOMock;
import com.epam.models.Ticket;
import com.epam.models.User;
import com.epam.security.Roles;
import beans.services.UserService;
import beans.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

@Configuration
public class TestUserServiceConfiguration {

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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDAOMock userDAO() {
        return new UserDAOMock(Arrays.asList(testUser1(), testUser2()));
    }

    @Bean
    public List<Ticket> tickets() {
        return Arrays.asList();
    }

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

    @Bean(name = "testUserServiceImpl")
    public UserService userServiceImpl() {
        return new UserServiceImpl(userDAO(), bookingBookingDAO());
    }
}
