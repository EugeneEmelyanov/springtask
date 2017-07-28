package beans.controllers;

import beans.dto.ImportDTO;
import com.epam.models.Event;
import com.epam.models.User;
import beans.services.EventService;
import beans.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Yauhen_Yemelyanau on 7/7/2017.
 */
@Controller()
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("eventServiceImpl")
    private EventService eventService;

    @RequestMapping(method = RequestMethod.POST)
    public void upload(@RequestParam("file") MultipartFile file,
                       HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ImportDTO data = mapper.readValue(file.getBytes(), ImportDTO.class);
        saveEvents(data.getEvents());
        saveUsers(data.getUsers());
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    private void saveUsers(List<User> users) {
        users.stream()
                .forEach(u -> userService.register(u));
    }

    private void saveEvents(List<Event> events) {
        events.stream()
                .forEach(e -> eventService.create(e));
    }

}
