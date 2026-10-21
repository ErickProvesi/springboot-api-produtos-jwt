
package com.example.meu_primeiro_springboot.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.meu_primeiro_springboot.dto.UsuarioResponse;
import com.example.meu_primeiro_springboot.model.Usuario;
import com.example.meu_primeiro_springboot.security.JwtUtil;
import com.example.meu_primeiro_springboot.services.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;

    public AuthController(
            UsuarioService usuarioService,
            AuthenticationManager authenticationManager) {

        this.usuarioService = usuarioService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Username e senha são obrigatórios"));
        }

        Usuario usuario = usuarioService.registrarUsuario(
                username, password);

        UsuarioResponse response = new UsuarioResponse(
                usuario.getId(),
                usuario.getUsername()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("erro", "Username e senha são obrigatórios"));
        }

        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username, password)
            );

            String token = JwtUtil.generateToken(
                    authentication.getName());

            return ResponseEntity.ok(Map.of("token", token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("erro", "Credenciais inválidas"));
        }
    }
}
