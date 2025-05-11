package net.proselyte.springbootdemo.controller;

import net.proselyte.springbootdemo.model.BookingForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookingController {

    private final JavaMailSender mailSender;
    private final String notificationEmail;

    public BookingController(JavaMailSender mailSender,
                             @Value("${booking.notification.email}") String notificationEmail) {
        this.mailSender = mailSender;
        this.notificationEmail = notificationEmail;
    }

    @PostMapping("/reservation")
    public String handleReservation(@ModelAttribute BookingForm form, Model model) {
        // формируем письмо
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(notificationEmail);
        msg.setSubject("Новая заявка на бронирование");
        msg.setText(
                        "Имя: "    + form.getName()   + "\n" +
                        "Телефон: "+ form.getPhone()  + "\n" +
                        "Дата: "   + form.getDate()   + "\n" +
                        "Время: "  + form.getTime()   + "\n" +
                        "Гостей: " + form.getGuests()
        );
        mailSender.send(msg);

        // можно показать всплывающее сообщение на главной
        model.addAttribute("bookingSuccess", true);

        System.out.println("BookingSuccess: " + model.containsAttribute("bookingSuccess"));
        return "home";
    }
}
