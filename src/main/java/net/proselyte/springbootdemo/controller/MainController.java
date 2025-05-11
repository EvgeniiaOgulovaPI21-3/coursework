package net.proselyte.springbootdemo.controller;
import net.proselyte.springbootdemo.model.BookingForm;
import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 *  Класс MainController управляет данными в архитектуре паттерна Model-View-Controller;
 *  обрабатывает запросы от клиента для входа на страницу, выполняет необходимые операции и возвращает результат.
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model) {return "home";}

    @GetMapping("/author")
    public String author(Model model) {return "author";}

    @GetMapping("/login")
    public String login(Model model) {return "login";}

    @GetMapping("/signup")
    public String signup(Model model) {return "signup";}
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               @RequestParam String confirmPassword,
                               HttpServletRequest request)
                                throws ServletException {
        // Проверка совпадения паролей
        if (!user.getPassword().equals(confirmPassword)) {
            bindingResult.rejectValue("password", "error.user", "Пароли не совпадают");
        }

        // Проверка существования пользователя
        if (userService.existsByLogin(user.getLogin())) {
            bindingResult.rejectValue("login", "error.user", "Логин уже занят");
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        user.setRole("USER");
        // Сохранение пользователя и редирект
        userService.save(user);
        request.login(user.getLogin(), user.getPassword());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.logout(); // Завершаем сессию
        response.sendRedirect("/"); // Перенаправляем на страницу логина
        return null;
    }
    @GetMapping("/check-login")
    @ResponseBody
    public Map<String, Boolean> checkLoginAvailability(@RequestParam String login) {
        boolean exists = userService.existsByLogin(login);
        return Collections.singletonMap("exists", exists);
    }
    @ModelAttribute("bookingForm")
    public BookingForm bookingForm() {
        return new BookingForm();
    }


}
