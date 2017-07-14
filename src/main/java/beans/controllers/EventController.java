package beans.controllers;

import beans.dto.EventSeatsDTO;
import beans.models.Auditorium;
import beans.models.Event;
import beans.models.Ticket;
import beans.services.AuditoriumService;
import beans.services.BookingService;
import beans.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Yauhen_Yemelyanau on 7/5/2017.
 */
@Controller
@RequestMapping("/event")
public class EventController {

    public static final String EVENTS_VIEW = "Events";
    public static final String EVENT_TICKETS_VIEW = "EventTickets";

    @Autowired
    @Qualifier("eventServiceImpl")
    private EventService eventService;

    @Autowired
    @Qualifier("auditoriumServiceImpl")
    private AuditoriumService auditoriumService;

    @Autowired
    @Qualifier("bookingServiceImpl")
    private BookingService bookingService;

    @RequestMapping("/{id}/tickets")
    public String showEventTickets(@PathVariable("id") long eventId,
                                   @ModelAttribute("model") ModelMap model) {

        EventSeatsDTO seatsAvailability = eventService.getSeatsAvailability(eventId);
        model.addAttribute("eventTickets", seatsAvailability);

        return EVENT_TICKETS_VIEW;
    }

    @RequestMapping(params = {"auditoriumName"})
    public String showEvent(@RequestParam String auditoriumName,
                            @ModelAttribute("model") ModelMap model) {

        Objects.requireNonNull(auditoriumName, "AuditoriumName cannot be null");

        model.addAttribute("events", eventService.getAll()
                .stream()
                .filter(event -> event.getAuditorium().getName().equals(auditoriumName))
                .toArray());

        return EVENTS_VIEW;
    }

    @RequestMapping(params = {"eventName", "auditoriumName", "eventDate"})
    public String showEvents(@RequestParam String eventName,
                             @RequestParam String auditoriumName,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventDate,
                             @ModelAttribute("model") ModelMap model) {

        if (eventDate == null) {
            throw new IllegalArgumentException(String.format("to and from params are incorrect: from=%ta %tb %td %tT %tZ %tY", eventDate));
        }

        Auditorium auditorium = auditoriumService.getByName(auditoriumName);

        if (auditorium == null) {
            throw new IllegalArgumentException(String.format("cannot find auditorium for name: %s", auditoriumName));
        }

        model.addAttribute("events", Arrays.asList(eventService.getEvent(eventName, auditorium, eventDate)));

        return EVENTS_VIEW;

    }

    @RequestMapping(params = {"eventName"})
    public String showEvents(@RequestParam String eventName,
                             @ModelAttribute("model") ModelMap model) {

        model.addAttribute("events", eventService.getByName(eventName));

        return EVENTS_VIEW;
    }

    @RequestMapping(params = {"to"})
    public String showEvents(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                             @ModelAttribute("model") ModelMap model) {
        if (to == null) {
            throw new IllegalArgumentException(String.format("to and from params are incorrect: from=%ta %tb %td %tT %tZ %tY", to));
        }

        model.addAttribute("events", eventService.getNextEvents(to));

        return EVENTS_VIEW;
    }

    @RequestMapping(params = {"from", "to"})
    public String showEvents(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                             @ModelAttribute("model") ModelMap model) {
        if (from == null ||
                to == null ||
                to.isBefore(from)) {
            throw new IllegalArgumentException(String.format("to and from params are incorrect: from=%ta %tb %td %tT %tZ %tY and to=%ta %tb %td %tT %tZ %tY", from, to));
        }

        model.addAttribute("events", eventService.getForDateRange(from, to));

        return EVENTS_VIEW;
    }

    @RequestMapping("/list")
    public String showEvents(@ModelAttribute("model") ModelMap model) {
        model.addAttribute("events", eventService.getAll());
        return EVENTS_VIEW;
    }

}
