package com.mp.bantads.controllers;

import com.mp.venusian.models.Client;
import com.mp.venusian.services.ClientService;
import com.mp.venusian.util.HeaderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AccountController {
    @Autowired
    final ClientService clientService;
    @Autowired
    final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    final CustomAuthenticationProvider authenticationProvider;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid ClientRegisterDto clientRegisterDto) {
        if (clientRegisterDto.getCpf() != null && clientService.existsByCpf(clientRegisterDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cliente com esse cpf já existe.");
        }
        try {
            var client = storeNewClient(clientRegisterDto);
            return ResponseEntity.status(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid ClientLoginDto clientLoginDto) {

    }

    private Client storeNewClient(ClientRegisterDto clientRegisterDto) {
    }
}
