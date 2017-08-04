import com.epam.models.Auditorium;
import com.epam.models.Event;
import com.epam.models.Rate;
import com.epam.models.User;
import com.epam.restclient.EventResourceRestClient;
import com.epam.restclient.UserResourceRestClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by yauhen_yemelyanau on 7/28/17.
 */
public class Main {
    public static void main(String[] args) {
        testUsers();
        testEvents();

    }

    protected static void testEvents() {
        List<Event> events;
        EventResourceRestClient restClient = new EventResourceRestClient();
        events = restClient.getAllEvents();
        System.out.println(events);
        //TODO:figure out Auditorium deserialization
//        EventDTO event = restClient.create(getTestEvent());
//
//        System.out.println(event);
//
//        EventDTO newEvent = restClient.getById(event.getId());
//        System.out.println(newEvent);
//
//        restClient.removeEvent(event.getId());
//        System.out.println("removed " + (restClient.getAllEvents().stream()
//                .filter(u -> u.getId() == event.getId())
//                .findFirst().isPresent() ? "badly" : "succesfully"));

    }


    protected static void testUsers() {
        List<User> users;
        UserResourceRestClient restClient = new UserResourceRestClient();
        Random r = new Random();
        users = new UserResourceRestClient().getAllUsers();
        System.out.println(users);
        User user = restClient.createUser(User.newBuilder().withUserRoles("REGISTERED_USER")
                .withBirthday(LocalDate.parse("1988-02-18"))
                .withEmail("Santa_claus+" + r.nextInt() + "@epam.com")
                .withName("Santa Claus")
                .withPassword("3")
                .build());

        System.out.println(user);

        User newUser = restClient.getById(user.getId());
        System.out.println(newUser);

        restClient.removeUser(user.getId());
        System.out.println("removed " + (restClient.getAllUsers().stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst().isPresent() ? "badly" : "succesfully"));

    }

    static Event getTestEvent() {
        return new Event("Test event2", Rate.MID, 500.0, java.time.LocalDateTime.of(2016, 12, 6, 9, 35, 0),
                testHall2());
    }

    static Auditorium testHall2() {
        return new Auditorium(2, "Test auditorium 2", 8, Collections.singletonList(1));
    }
}
