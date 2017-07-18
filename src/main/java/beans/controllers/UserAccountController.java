package beans.controllers;

import beans.services.UserAccountService;
import beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yauhen_yemelyanau on 7/17/17.
 */
@Controller
@RequestMapping("/account")
public class UserAccountController {

//    private static final String USER_ACCOUNT_VIEW = "UserAccount";
//
//    @Autowired
//    @Qualifier("userAccountServiceImpl")
//    private UserAccountService userAccountService;
//
//    @Autowired
//    @Qualifier("userServiceImpl")
//    private UserService userService;
//
//    public String showAccounts(@ModelAttribute("model")ModelMap modelMap) {
//
//        userService.get
//
//        modelMap.addAttribute("userAccounts", )
//        return USER_ACCOUNT_VIEW;
//    }
}
