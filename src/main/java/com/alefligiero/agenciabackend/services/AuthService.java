package com.alefligiero.agenciabackend.services;

import com.alefligiero.agenciabackend.domain.role.Role;
import com.alefligiero.agenciabackend.domain.user.*;
import com.alefligiero.agenciabackend.mappers.UserMapper;
import com.alefligiero.agenciabackend.repositories.RoleRepository;
import com.alefligiero.agenciabackend.repositories.UserRepository;
import com.alefligiero.agenciabackend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO register(UserCreateDTO dto) {
        String password = passwordEncoder.encode(dto.password());
        Role role = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(password)
                .roles(Set.of(role))
                .build();

        User entity = userRepository.save(user);

        return UserMapper.toResponseDTO(entity);
    }

    public UserLoginResponseDTO loginUser(UserLoginDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(user);

        return UserLoginResponseDTO.builder()
                .token(token)
                .user(UserMapper.toResponseDTO(user))
                .build();
    }

}
