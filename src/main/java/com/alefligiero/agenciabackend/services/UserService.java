package com.alefligiero.agenciabackend.services;

import com.alefligiero.agenciabackend.domain.role.Role;
import com.alefligiero.agenciabackend.domain.role.RoleRequestDTO;
import com.alefligiero.agenciabackend.domain.user.*;
import com.alefligiero.agenciabackend.mappers.RoleMapper;
import com.alefligiero.agenciabackend.mappers.UserMapper;
import com.alefligiero.agenciabackend.repositories.RoleRepository;
import com.alefligiero.agenciabackend.repositories.UserRepository;
import com.alefligiero.agenciabackend.services.exceptions.DatabaseException;
import com.alefligiero.agenciabackend.services.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllUsers(Pageable pageable, String search) {
        return userRepository.findAllByFirstNameIgnoreCaseStartingWithOrLastNameIgnoreCaseStartingWith(pageable, search, search)
                .map(UserMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(UUID id) {
        return userRepository
                .findById(id)
                .map(UserMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found id: " + id));
    }

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {
        String password = passwordEncoder.encode(dto.password());
        Role role = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        Set<Role> userRoles = dto.roles() == null
                ? Set.of(role)
                : roleRepository
                .findRolesByNameIn(
                        dto.roles()
                            .stream()
                            .map(RoleRequestDTO::name)
                            .collect(Collectors.toSet()));

        return UserMapper.toResponseDTO(userRepository.save(UserMapper.toEntity(dto, password, userRoles)));
    }

    @SneakyThrows
    @Transactional
    public UserResponseDTO updateUser(UUID id, UserEditDTO dto) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found id: " + id));

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());

        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    @Transactional
    public UserResponseDTO updateUserEmail(UUID id, UserEditEmailDTO dto) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found id: " + id));

        user.setEmail(dto.newEmail());

        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    @SneakyThrows
    @Transactional
    public boolean updateUserPassword(UUID id, UserEditPasswordDTO dto) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found id: " + id));

        if (!passwordEncoder.matches(dto.oldPassword(), user.getPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));

        userRepository.save(user);

        return true;
    }

    @SneakyThrows
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found id: " + id);
        }
        try {
            userRepository.deleteById(id);
        } catch (DatabaseException e) {
            throw new RuntimeException("Error deleting user id: " + id);
        }
    }

}
