package com.alefligiero.agenciabackend.domain.user;

import com.alefligiero.agenciabackend.domain.role.RoleRequestDTO;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserCreateDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        Set<RoleRequestDTO> roles
) {
}
