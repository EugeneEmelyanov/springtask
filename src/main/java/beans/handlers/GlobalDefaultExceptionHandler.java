package beans.handlers;

import beans.exceptions.IncufficientMoneyException;
import beans.services.SpringAdvancedUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yauhen_Yemelyanau on 7/5/2017.
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "Error";

    @ExceptionHandler(value= IncufficientMoneyException.class)
    public void notSufficientHandler(HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SpringAdvancedUserDetailsService.UserPrincipal principal = (SpringAdvancedUserDetailsService.UserPrincipal) authentication.getPrincipal();
        response.sendRedirect("/user/"+principal.getUser().getId()+"/?show_error=true");
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req,
                                            Exception e) {

        ModelAndView mv = new ModelAndView(DEFAULT_ERROR_VIEW);
        mv.getModel().put("ex", e);

        return mv;
    }
}
