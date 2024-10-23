package br.com.santander.authenticationservice.controller;

import br.com.santander.authenticationservice.dto.Login;
import br.com.santander.authenticationservice.dto.ResponseDto;
import br.com.santander.authenticationservice.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class AuthenticationController {

    private JWTUtil jwtUtil;
    private final AuthenticationProvider authenticationProvider;

    public AuthenticationController(JWTUtil jwtUtil, AuthenticationProvider authenticationProvider) {
        this.jwtUtil = jwtUtil;
        this.authenticationProvider = authenticationProvider;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> login(@Validated @RequestBody Login login) {
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()));
        } catch (Exception ex) {
            throw new RuntimeException("Invalid email or password.");
        }

        String TOKEN = jwtUtil.generateToken(login.getEmail());

        return ResponseEntity.ok(ResponseDto.builder()
                        .token(TOKEN)
                        .build());
    }
}
