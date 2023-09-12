package com.example.ecommerce.ecommerce.Controller;

import com.example.ecommerce.ecommerce.Entity.User;
import com.example.ecommerce.ecommerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserLRController {
    @Autowired
    private UserService userService;
    @GetMapping("/signin")
    public String login(){
        return "login";
    }
    @GetMapping("/register")
    public String register(){
        return "Registration";
    }

    @PostMapping("/saveUser")
    public String registerUser(@ModelAttribute("user") User user, @RequestParam("role") String[] role) {
        user.setRoles(role); // Set the role based on the selected option in the form
        userService.saveUser(user); // Save the user to the database
        return "redirect:/login"; // Redirect to the login page after successful registration
    }
    @GetMapping("/About")
    public String About() {
        return "about";
    }
}
