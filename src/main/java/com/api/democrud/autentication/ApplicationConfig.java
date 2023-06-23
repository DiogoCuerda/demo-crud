package com.api.democrud.autentication;

import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.api.democrud.exception.CustomExceptionHandler.USUARIO_NENCONTRADO;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UsuarioRepository repository;
    @Bean
    public UserDetailsService userDetailsService(){
        return nome ->  repository.findByNome(nome)
                .orElseThrow(() -> new ElementoNencontradoException(String.format(USUARIO_NENCONTRADO)));
    }

    @Bean
    public AuthenticationProvider autenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{

        return config.getAuthenticationManager();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
