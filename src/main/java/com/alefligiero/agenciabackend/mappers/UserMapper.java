package com.alefligiero.agenciabackend.mappers;

import com.alefligiero.agenciabackend.domain.role.Role;
import com.alefligiero.agenciabackend.domain.role.RoleResponseDTO;
import com.alefligiero.agenciabackend.domain.user.User;
import com.alefligiero.agenciabackend.domain.user.UserCreateDTO;
import com.alefligiero.agenciabackend.domain.user.UserEditDTO;
import com.alefligiero.agenciabackend.domain.user.UserResponseDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponseDTO toResponseDTO(User user) {
        Set<RoleResponseDTO> roles = user.getRoles().stream().map(RoleMapper::toResponseDTO).collect(Collectors.toSet());
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roles(roles)
                .build();
    }

    public static User toEntity(UserCreateDTO dto, String password, Set<Role> roles) {
        return User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .password(password)
                .roles(roles)
                .build();
    }

    public static void copyDtoToEntity(UserEditDTO dto, User user) {
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
    }

}
