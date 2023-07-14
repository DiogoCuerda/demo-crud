package com.api.democrud.service;

import com.api.democrud.autentication.AutenticationRequest;
import com.api.democrud.autentication.AutenticationResponse;
import com.api.democrud.autentication.JwtService;
import com.api.democrud.exception.ElementoNencontradoException;
import com.api.democrud.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AutenticationResponse authenticate(AutenticationRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getNome(), request.getSenha());
        System.out.println("pass: " + passwordEncoder.encode(request.getSenha()));
        System.out.println(usernamePasswordAuthenticationToken.toString());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        var usuario = usuarioRepository.findByNome(request.getNome()).orElseThrow();
        var jwtToken = jwtService.generateToken(usuario);
        var refreshToken = jwtService.generateRefreshToken(usuario);
        return AutenticationResponse.builder().acessToken(jwtToken).refreshToken(refreshToken).build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final  String userName;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUserName(refreshToken);
        if (userName != null){
            var userDetails = this.usuarioRepository.findByNome(userName).orElseThrow(()-> new ElementoNencontradoException("Usuario não encontrado"));
            if (jwtService.isTokenValid(refreshToken,userDetails)){
                var acessToken = jwtService.generateToken(userDetails);
                var authResponse = AutenticationResponse.builder()
                        .acessToken(acessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
