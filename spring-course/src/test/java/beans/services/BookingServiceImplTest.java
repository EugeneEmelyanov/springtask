package beans.services;

import beans.configuration.AppConfiguration;
import beans.configuration.TestBookingServiceConfiguration;
import beans.configuration.TestConfig;
import beans.configuration.db.DataSourceConfiguration;
import beans.configuration.db.DbSessionFactory;
import beans.daos.mocks.BookingDAOBookingMock;
import beans.daos.mocks.DBAuditoriumDAOMock;
import beans.daos.mocks.EventDAOMock;
import beans.daos.mocks.UserDAOMock;
import com.epam.models.Event;
import com.epam.models.Ticket;
import com.epam.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfiguration.class, DataSourceConfiguration.class, DbSessionFactory.class,
        TestBookingServiceConfiguration.class, TestConfig.class})
@Transactional
public class BookingServiceImplTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("#{testBookingServiceImpl}")
    private BookingService bookingService;

    @Autowired
    private BookingDAOBookingMock bookingDAOBookingMock;
    @Autowired
    private EventDAOMock eventDAOMock;
    @Autowired
    private UserDAOMock userDAOMock;
    @Autowired
    private DBAuditoriumDAOMock auditoriumDAOMock;

    @Before
    public void init() {
        auditoriumDAOMock.init();
        userDAOMock.init();
        eventDAOMock.init();
        bookingDAOBookingMock.init();
    }

    @After
    public void cleanup() {
        auditoriumDAOMock.cleanup();
        userDAOMock.cleanup();
        eventDAOMock.cleanup();
        bookingDAOBookingMock.cleanup();
    }

    @Test
    public void testGetTicketsForEvent() throws Exception {
        Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
        Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
        List<Ticket> ticketsForEvent = bookingService.getTicketsForEvent(testEvent1.getName(),
                testEvent1.getAuditorium().getName(),
                testEvent1.getDateTime());
        assertEquals("Tickets should match", Arrays.asList(ticket), ticketsForEvent);
    }

    @Test(expected = RuntimeException.class)
    public void testBookTicket_NotRegistered() throws Exception {
        Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
        List<Ticket> before = bookingService.getTicketsForEvent(testEvent1.getName(),
                testEvent1.getAuditorium().getName(),
                testEvent1.getDateTime());
        User newUser = new User(UUID.randomUUID().toString(), UUID.randomUUID().toString(), LocalDate.now());
        Ticket newTicket = new Ticket(testEvent1, Arrays.asList(3, 4), newUser, 0.0);
        bookingService.bookTicket(newUser, newTicket);
    }

    @Test(expected = RuntimeException.class)
    public void testBookTicket_AlreadyBooked() throws Exception {
        Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
        List<Ticket> before = bookingService.getTicketsForEvent(testEvent1.getName(),
                testEvent1.getAuditorium().getName(),
                testEvent1.getDateTime());
        User testUser2 = (User) applicationContext.getBean("testUser2");
        Ticket newTicket = new Ticket(testEvent1, Arrays.asList(3, 4), testUser2, 0.0);
        bookingService.bookTicket(testUser2, newTicket);
    }

    @Test
    public void testBookTicket() throws Exception {
        Event testEvent1 = (Event) applicationContext.getBean("testEvent1");
        List<Ticket> before = bookingService.getTicketsForEvent(testEvent1.getName(),
                testEvent1.getAuditorium().getName(),
                testEvent1.getDateTime());
        User testUser1 = (User) applicationContext.getBean("testUser1");
        Ticket newTicket = new Ticket(testEvent1, Arrays.asList(5, 6), testUser1, 0.0);
        Ticket bookedTicket = bookingService.bookTicket(testUser1, newTicket);
        List<Ticket> after = bookingService.getTicketsForEvent(testEvent1.getName(),
                testEvent1.getAuditorium().getName(),
                testEvent1.getDateTime());
        before.add(bookedTicket);
        assertTrue("Events should change", after.containsAll(before));
        assertTrue("Events should change", before.containsAll(after));
    }

    @Test
    public void testGetTicketPrice() throws Exception {
        Ticket ticket = (Ticket) applicationContext.getBean("testTicket1");
        Event event = ticket.getEvent();
        double ticketPrice = bookingService.getTicketPrice(event.getName(), event.getAuditorium().getName(),
                event.getDateTime(), ticket.getSeatsList(),
                ticket.getUser());
        Assert.assertEquals("Price is wrong", 297.59999999999997, ticketPrice, 0.00001);
    }

}
