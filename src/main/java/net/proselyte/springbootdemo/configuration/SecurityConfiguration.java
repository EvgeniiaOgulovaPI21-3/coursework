package net.proselyte.springbootdemo.configuration;

import net.proselyte.springbootdemo.service.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
/**
 * Конфигурационный класс содержит настройки для авторизации и аутентификации пользователей;
 * создает цепочку фильтров безопасности с настройками HTTP-запросов.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity

                .authorizeRequests()
                .antMatchers("/","/home", "/author", "/login", "/registration", "/registration/**", "/css/**", "/js/**", "/images/**", "/reservation/", "/reservation/**").permitAll()  // Разрешаем доступ без авторизации к этим страницам
                .anyRequest().authenticated()  // Для остальных страниц нужна авторизация
                .and()
                .formLogin()
                .loginPage("/login")  // Страница для входа
                .loginProcessingUrl("/execute_login")  // URL для обработки POST запроса входа
//                .defaultSuccessUrl("/", true)  // Перенаправление на главную страницу после успешного входа
                .failureUrl("/login?error=true")  // Перенаправление на страницу входа с ошибкой
                .and()
                .logout()
                .logoutSuccessUrl("/")  // Перенаправление на главную страницу после выхода
                .permitAll()  // Разрешаем выход для всех
                .and().build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
