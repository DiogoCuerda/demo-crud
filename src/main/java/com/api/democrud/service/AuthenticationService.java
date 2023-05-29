package com.api.democrud.service;

import com.api.democrud.autentication.AutenticationRequest;
import com.api.democrud.autentication.AutenticationResponse;
import com.api.democrud.autentication.JwtService;
import com.api.democrud.repositorie.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public AutenticationResponse authenticate(AutenticationRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getNome(),request.getSenha());
     System.out.println(passwordEncoder.encode(request.getSenha()));
        //   request.setNome("admin");
     //   System.out.println();
        System.out.println(usernamePasswordAuthenticationToken.toString());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
          var usuario = usuarioRepository.findByNome(request.getNome())
                        .orElseThrow();
          var jwtToken = jwtService.generateToken(usuario);
          return AutenticationResponse.builder().token(jwtToken).build();
    }
}
