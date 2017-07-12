package beans.configuration;

import beans.models.Auditorium;
import beans.models.Event;
import beans.models.Rate;
import beans.models.User;
import beans.security.Roles;
import beans.services.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Set-up db schema, previously the same code was executed in Main.class
 */
@Component()
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON) //it is by default, just want to make sure
//that everyone understand, why such event handlers works
public class DataLoader {

    private boolean isInitialized = false;

    private ApplicationContext ctx;

    @EventListener({ContextRefreshedEvent.class})
    void contextRefresh(ContextRefreshedEvent event) {
        if (!isInitialized) {
            ctx = event.getApplicationContext();
            setUpDbData();
            isInitialized = true;
        }
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }

    //this copied from Main.class
    private void setUpDbData() {
        AuditoriumService auditoriumService = (AuditoriumService) ctx.getBean("auditoriumServiceImpl");
        BookingService bookingService = (BookingService) ctx.getBean("bookingServiceImpl");
        EventService eventService = (EventService) ctx.getBean("eventServiceImpl");
        UserService userService = (UserService) ctx.getBean("userServiceImpl");
        DiscountService discountService = (DiscountService) ctx.getBean("discountServiceImpl");

        String email = "eugene.v.emelyanov@gmail.com";
        String name = "Eugene Emelyanov";
        String eventName = "The revenant";
        String auditoriumName = "Blue hall";
        Auditorium blueHall = auditoriumService.getByName(auditoriumName);
        Auditorium yellowHall = auditoriumService.getByName("Yellow hall");
        Auditorium redHall = auditoriumService.getByName("Red hall");
        LocalDateTime dateOfEvent = LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(15, 45, 0));


        userService.register(User.newBuilder()
                .withName(name)
                .withEmail(email)
                .withBirthday(LocalDate.now())
                .withPassword("12345")
                .withUserRoles(Roles.REGISTERED_USER.getAuthority() + "," + Roles.BOOKING_MANAGER.getAuthority())
                .build()
        );
        userService.register(User.newBuilder()
                .withName(name)
                .withEmail("yy")
                .withBirthday(LocalDate.of(1992, 4, 29))
                .withPassword("12345")
                .withUserRoles(String.join(",", new String[]{Roles.REGISTERED_USER.getAuthority(), Roles.BOOKING_MANAGER.getAuthority()}))
                .build());


        User userByEmail = userService.getUserByEmail(email);
        System.out.println("User with email: [" + email + "] is " + userByEmail);
        System.out.println();

        System.out.println("All users with name: [" + name + "] are: ");
        userService.getUsersByName(name).forEach(System.out::println);
        System.out.println();

        Event event1 = eventService.create(
                new Event(eventName, Rate.HIGH, 60, LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(9, 0, 0)),
                        blueHall));
        System.out.println();
        System.out.println("Event by name: " + eventService.getByName(event1.getName()));
        System.out.println();
        eventService.create(new Event(eventName, Rate.HIGH, 60, dateOfEvent, blueHall));
        Event event2 = eventService.create(
                new Event(eventName, Rate.HIGH, 60, LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(21, 18, 0)),
                        blueHall));
        eventService.create(
                new Event(eventName, Rate.HIGH, 90, LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(21, 18, 0)),
                        redHall));
        Event event = new Event(eventName, Rate.HIGH, 150,
                LocalDateTime.of(LocalDate.of(2016, 2, 5), LocalTime.of(21, 18, 0)), yellowHall);
        event = eventService.create(event);

        System.out.println("List of all events:");
        eventService.getAll().forEach(System.out::println);
        System.out.println();

        System.out.println(
                "Discount for user: [" + email + "] for event: [" + eventName + "] in auditorium: [" + auditoriumName +
                        "] on date: [" + dateOfEvent + "] is [" +
                        discountService.getDiscount(userByEmail, eventService.getEvent(eventName, blueHall, dateOfEvent))
                        + "]");
        System.out.println();

    }
}
