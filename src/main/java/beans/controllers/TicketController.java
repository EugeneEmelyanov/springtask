package beans.controllers;

import beans.services.UserService;
import beans.views.TicketsPdfView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

/**
 * Created by Yauhen_Yemelyanau on 7/5/2017.
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {

    private static final String TICKET_VIEW = "Tickets";
    private static final String TICKET_VIEW_PDF = "Tickets_pdf";

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping(path = "/list", headers = {"Accept=application/pdf"})
    public ModelAndView showTicketsPDF() {

        ModelAndView vm = new ModelAndView(TICKET_VIEW_PDF);
        vm.setView(new TicketsPdfView());

        vm.addObject("tickets", userService.getBookedTickets());

        return vm;
    }

    @RequestMapping("/list")
    public String showTickets(@ModelAttribute("model") ModelMap model) {

        model.addAttribute("tickets", userService.getBookedTickets());

        return TICKET_VIEW;
    }

    @RequestMapping(path = "/{userId}", headers = {"Accept=application/pdf"})
    public ModelAndView showTicketsForUserPDF(@PathVariable Long userId) {

        ModelAndView vm = new ModelAndView(TICKET_VIEW_PDF);
        vm.setView(new TicketsPdfView());

        vm.addObject("tickets", userService.getBookedTicketsByUser(userId));
        vm.addObject("user", userService.getById(userId));

        return vm;
    }

    @RequestMapping("/{userId}")
    public String showTickets(@PathVariable Long userId,
                              @ModelAttribute("model") ModelMap model) {
        Objects.requireNonNull(userId, "UserId cannot be null");

        model.addAttribute("tickets", userService.getBookedTicketsByUser(userId));
        model.addAttribute("user", userService.getById(userId));

        return TICKET_VIEW;
    }

}
