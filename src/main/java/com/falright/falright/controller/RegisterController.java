package com.falright.falright.controller;

import com.falright.falright.model.Users;
import com.falright.falright.repository.ValidationGroups;
import com.falright.falright.service.EmailServiceImpl;
import com.falright.falright.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class RegisterController {
    @Autowired private EmailServiceImpl emailService;
    private final UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder databinder) {
        StringTrimmerEditor stringTimerEditor = new StringTrimmerEditor(true);
        databinder.registerCustomEditor(String.class,stringTimerEditor);
    }
    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Validated({ValidationGroups.Register.class}) @ModelAttribute("user") Users user,
                           BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)
    {

        if (userService.emailExists(user.getEmail())) {
            bindingResult.addError(new FieldError("user", "email", "Podany E-mail już istnieje!"));
        }

        if (userService.usernameExists(user.getUsername())) {
            bindingResult.addError(new FieldError("user", "username", "Podana nazwa użytkownika już istnieje!"));
        }

        if (user.getPassword() != null &&  user.getRpassword() != null) {
            if(!user.getPassword().equals(user.getRpassword())) {
                bindingResult.addError(new FieldError("user", "rpassword", "Hasła muszą być takie same!"));
            }
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        redirectAttributes.addFlashAttribute("message", "Rejestracja przebiegła pomyślnie!");
        userService.registerUser(user);

        emailService.sendEmail(user.getEmail(), "Rejestracja", "Witaj " + user.getUsername() + "! Twoje konto zostało utworzone pomyślnie!");

        model.addAttribute("user", user);
        return "redirect:/login";
    }

}
