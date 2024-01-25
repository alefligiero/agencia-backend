package com.alefligiero.agenciabackend.domain.user;

import com.alefligiero.agenciabackend.domain.role.RoleResponseDTO;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UserResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        Set<RoleResponseDTO> roles) {
}
