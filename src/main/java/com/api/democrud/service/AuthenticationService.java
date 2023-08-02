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
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AutenticationResponse authenticate(AutenticationRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getNome(), request.getSenha());
        HashMap claims = new HashMap<>();

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        var usuario = usuarioRepository.findByNome(request.getNome()).orElseThrow();
        claims.put("tokenType","access");
        var jwtToken = jwtService.generateToken(claims,usuario);
        claims.put("tokenType","refresh");
        var refreshToken = jwtService.generateRefreshToken(claims,usuario);
        return AutenticationResponse.builder().acessToken(jwtToken).refreshToken(refreshToken).build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final  String userName;
        HashMap claims = new HashMap<>();
        claims.put("tokenType","access");
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);

        if (!jwtService.isRefreshToken(refreshToken))
        {
            return;
        }
        userName = jwtService.extractUserName(refreshToken);
        if (userName != null){
            var userDetails = this.usuarioRepository.findByNome(userName).orElseThrow(()-> new ElementoNencontradoException("Usuario não encontrado"));
            if (jwtService.isTokenValid(refreshToken,userDetails)){
                var acessToken = jwtService.generateToken(claims,userDetails);
                var authResponse = AutenticationResponse.builder()
                        .acessToken(acessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
