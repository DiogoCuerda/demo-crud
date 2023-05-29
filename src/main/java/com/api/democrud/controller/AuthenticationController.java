package com.api.democrud.controller;

import com.api.democrud.autentication.AutenticationRequest;
import com.api.democrud.autentication.AutenticationResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.democrud.service.AuthenticationService;


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
}
