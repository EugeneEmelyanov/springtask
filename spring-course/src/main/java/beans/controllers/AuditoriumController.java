package beans.controllers;

import com.epam.models.Auditorium;
import beans.services.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by Yauhen_Yemelyanau on 7/5/2017.
 */
@Controller()
@RequestMapping("/auditorium")
public class AuditoriumController {

    private static final String AUDITORIUM_VIEW = "Auditoriums";

    @Autowired
    @Qualifier("auditoriumServiceImpl")
    private AuditoriumService auditoriumService;

    @RequestMapping("/list")
    public String showAuditorium(@RequestParam(required = false) String name,
                                 @ModelAttribute("model") ModelMap modelMap) {

        List<Auditorium> auditoriumList;
        if (name == null) {
            auditoriumList = auditoriumService.getAuditoriums();
        } else {

            Auditorium auditorium = auditoriumService.getByName(name);

            if (Objects.isNull(auditorium)) {
                throw new IllegalArgumentException(String.format("Cannot find auditorium for name %s", name));
            }
            auditoriumList = Arrays.asList(auditorium);
        }

        modelMap.addAttribute("auditoriums", auditoriumList);

        return AUDITORIUM_VIEW;
    }
}
