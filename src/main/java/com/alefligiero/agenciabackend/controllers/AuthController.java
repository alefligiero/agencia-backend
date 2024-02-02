package com.alefligiero.agenciabackend.controllers;

import com.alefligiero.agenciabackend.domain.user.UserCreateDTO;
import com.alefligiero.agenciabackend.domain.user.UserLoginDTO;
import com.alefligiero.agenciabackend.domain.user.UserLoginResponseDTO;
import com.alefligiero.agenciabackend.domain.user.UserResponseDTO;
import com.alefligiero.agenciabackend.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginDTO dto) {
        UserLoginResponseDTO userLoginResponseDTO = authService.loginUser(dto);
        return ResponseEntity.ok().body(userLoginResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserCreateDTO dto) {
        UserResponseDTO userResponseDTO = authService.register(dto);
        URI uri = URI.create("/api/v1/users/" + userResponseDTO.id());
        return ResponseEntity.created(uri).body(userResponseDTO);
    }

}
