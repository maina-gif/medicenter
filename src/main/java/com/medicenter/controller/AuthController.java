package com.medicenter.controller;

import com.medicenter.dto.LoginRequest;
import com.medicenter.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    public record LoginResponse(String mensagem, String email, String perfil) {}
    private final AuthenticationManager authenticationManager; private final UsuarioRepository repository;
    public AuthController(AuthenticationManager authenticationManager, UsuarioRepository repository) { this.authenticationManager = authenticationManager; this.repository = repository; }
    @PostMapping("/login") public LoginResponse login(@Valid @RequestBody LoginRequest r) { Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(r.email(), r.senha())); var u = repository.findByEmailIgnoreCase(auth.getName()).orElseThrow(); return new LoginResponse("Login realizado. Use HTTP Basic nas próximas requisições.", u.getEmail(), u.getPerfil().name()); }
    @GetMapping("/me") public LoginResponse me(Authentication auth) { var u = repository.findByEmailIgnoreCase(auth.getName()).orElseThrow(); return new LoginResponse("Sessão autenticada.", u.getEmail(), u.getPerfil().name()); }
}
