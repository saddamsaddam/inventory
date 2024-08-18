package com.os.inventory.controller;

import com.os.inventory.entity.User;
import com.os.inventory.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    /*
     * @Method description: This function is called from Default URL
     * @param: No
     * @return: View */
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login(User user) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login/login1");
        return modelAndView;
    }

    /*
     * @Method description: This function is called from Registration URL
     * @param: No
     * @return: View */
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("login/registration");
        return modelAndView;
    }

    /*
     * @Method description: This function is called from Registration Button
     * @param: No
     * @return: View and View*/
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUserName(), true);
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "User already exists.");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("login/registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User Registered Successfully.");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login/registration");

        }
        return modelAndView;
    }

    /*
     * @Method description: This function is called from default login success url
     * @param: No
     * @return: View */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView home(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName(), true);

        httpSession.setAttribute("userId", user.getUserName());
        httpSession.setAttribute("userName", user.getFirstName() + " " + user.getLastName());

        modelAndView.setViewName("login/home");
        return modelAndView;
    }

}
