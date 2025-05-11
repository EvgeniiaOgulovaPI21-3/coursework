package net.proselyte.springbootdemo.security;

import net.proselyte.springbootdemo.model.User;
import net.proselyte.springbootdemo.details.UserDetails;
import net.proselyte.springbootdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import net.proselyte.springbootdemo.service.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        Object credentials = authentication.getCredentials();
        if (credentials == null) throw new BadCredentialsException("Не удалось войти в аккаунт");
        String password = credentials.toString();

        UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(username);
        User user = userDetails.getUser();

        if (user == null) throw new BadCredentialsException("Пользователь с таким логином не найден");
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Пароль введён неверно");
        }

        return new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}