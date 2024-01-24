package com.alefligiero.agenciabackend.domain.role;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RoleResponseDTO(UUID id, String name) {
}
