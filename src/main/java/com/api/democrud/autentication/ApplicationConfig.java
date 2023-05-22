package com.api.democrud.autentication;

import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.repositorie.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

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
}
