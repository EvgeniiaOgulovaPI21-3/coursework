package net.proselyte.springbootdemo.controller;  // или configuration

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addAuthAttributes(Authentication authentication, Model model, HttpSession session) {
        boolean authorized = (authentication != null);
        model.addAttribute("authorized", authorized);

        boolean isAdmin = authorized &&
                authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        if (authorized) {
            session.setAttribute("login", authentication.getName());
        }
    }
}
