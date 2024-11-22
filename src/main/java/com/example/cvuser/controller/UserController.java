package com.example.cvuser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
 
import com.example.cvuser.model.CredentialModel;
import com.example.cvuser.model.UserModel;
import com.example.cvuser.service.CredentialService;
import com.example.cvuser.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController 
{

    private final UserService userService;
    private final CredentialService credentialService;

    @Autowired
    public UserController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UserModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new UserModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel, Model model) 
    {
    System.out.println("register request: " + userModel);
    try {
        UserModel registeredUser = userService.registerUser(userModel.getLogin(), userModel.getPassword(), userModel.getEmail());
        return "redirect:/login";
    } catch (IllegalArgumentException e) 
    { 
        model.addAttribute("registerRequest", userModel); 
        model.addAttribute("errorMessage", e.getMessage()); 
        return "register_page"; 
    }
    }


    @GetMapping("/logout")
    public String logout() 
    {
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel, Model model, HttpServletRequest request) 
    {
        System.out.println("login request:" + userModel);
        UserModel authenticated = userService.authenticate(userModel.getLogin(), userModel.getPassword());
        if (authenticated != null) 
        {
            request.getSession().setAttribute("user", authenticated);
            return "redirect:/credentials?userId=" + authenticated.getId(); 
        } else 
        {
            model.addAttribute("errorMessage", "Invalid username/password.");
            model.addAttribute("loginRequest", userModel);
            return "login_page";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) 
    {
        request.getSession().invalidate();
    
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        return "redirect:/login";  
    }


}




