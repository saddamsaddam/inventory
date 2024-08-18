package com.os.inventory.controller;

import com.os.inventory.entity.User;
import com.os.inventory.repository.UserRepository;
import com.os.inventory.services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Qualifier("userRepository")
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/editprofile")
    public String userProfileEdit(@RequestParam Map<String, Object> parameters, Model model) {
        String url = null;
        String userName = (String) parameters.get("id");
        User user = userService.findUserByUserName(userName, true);
        model.addAttribute("user", user);
        url = "view/userprofile/show";
        return url;
    }

    @RequestMapping(value = "/user/updateuserprofile", method = RequestMethod.POST)
    @ResponseBody
    public String updateUserProfile(@RequestParam Map<String, Object> parameters) throws IOException {
        String return_status = "";
        String user_name = (String) parameters.get("userName");

        try {
            if (parameters.get("id") != null && !Objects.equals(user_name, "")) {
                User user = userService.findUserByUserName(user_name.trim(), true);

                user.setEmail((String) parameters.get("email"));
                user.setFirstName((String) parameters.get("first_name"));
                user.setLastName((String) parameters.get("last_name"));

                userRepository.save(user);
                return_status = "success";
            } else {
                return_status = "error";
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return return_status;
    }

    @GetMapping("/user/change-password")
    public String userChangePassword(HttpServletRequest request, ModelMap model) {
        model.addAttribute("data", new User());
        String url;
        String userId = "";
        String userName = "";
        HttpSession session = request.getSession();

        if (session.getAttribute("userId") != null) {
            userId = (String) session.getAttribute("userId");
            userName = (String) session.getAttribute("userName");
        }

        User user = userService.findUserByUserName(userId, true);
        model.addAttribute("user", user);
        url = "view/changepassword/show";
        return url;
    }

    @PostMapping("user/update-password")
    public String userUpdatePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 HttpServletRequest request, Model model) {

        String userName = "";

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Password & Confirm Password Not matched!");
            return "/view/changepassword/show";
        }

        HttpSession session = request.getSession();
        if (session.getAttribute("userId") != null) {
            userName = (String) session.getAttribute("userId");
        }

        try {
            userService.updatePassword(userName, oldPassword, newPassword);
            model.addAttribute("successMessage", "Password Change Successfully.");
            return "/view/changepassword/show";

        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/view/changepassword/show";
        }
    }

}
