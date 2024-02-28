package com.forums.app.controllers;

import com.forums.app.dto.UserDTO;
import com.forums.app.modal.User;
import com.forums.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;
@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    public HomeController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/home")
    public String home(Model model) {
        List<UserDTO> dataList = userService.findAll();
        model.addAttribute("data", dataList);
        return "home"; // Assuming "home" is your Thymeleaf template name
    }
    @GetMapping("/signup")
    public String showSignUpPage(Model model, String error, String logout) {


        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping("/signin")
    public String showSignInPage(Model model) {


        model.addAttribute("userForm", new User());
        return "registration";
    }


    @PostMapping("/signin")
    public String showSignInPage(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);

        return "home";
    }
}
