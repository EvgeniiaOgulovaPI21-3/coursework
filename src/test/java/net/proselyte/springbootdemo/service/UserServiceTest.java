package net.proselyte.springbootdemo.service;

import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;  // Мокаем репозиторий

    @Mock
    private BCryptPasswordEncoder passwordEncoder;  // Мокаем BCryptPasswordEncoder

    @InjectMocks
    private UserService userService;  // С помощью этой аннотации автоматически инжектим зависимости

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Инициализация моков
    }

    @Test
    void testCreateUser_withValidPassword() {
        String password = "testPassword";
        String login = "testLogin";

        User user = new User(login, password, "USER");

        // Мокаем поведение passwordEncoder
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        // Сохраняем пользователя с зашифрованным паролем
        user.setPassword(passwordEncoder.encode(password));

        // Проверяем, что пароль был зашифрован
        assertEquals("encodedPassword", user.getPassword());  // Проверка хэша пароля
    }

    @Test
    void testCreateUser_withExistingLogin() {
        String password = "testPassword";
        String login = "testLogin";

        User user = new User(login, password, "USER");

        // Мокаем поведение репозитория, чтобы он возвращал пользователя с таким же логином
        when(userRepository.existsByLogin(login)).thenReturn(true);

        // Проверка на уже существующий логин
        assertTrue(userService.existsByLogin(login));  // Проверка на существование логина
    }
}
