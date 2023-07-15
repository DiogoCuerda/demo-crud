package com.api.democrud.controller;

import com.api.democrud.autentication.AutenticationRequest;
import com.api.democrud.autentication.AutenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.democrud.service.AuthenticationService;

import java.io.IOException;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    @GetMapping("/authenticate")
    public ResponseEntity<AutenticationResponse> register(@RequestBody AutenticationRequest request){
         return ResponseEntity.ok(service.authenticate(request));

    }

    @GetMapping("/refresh-token")
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
      service.refreshToken(request, response);
    }


}
