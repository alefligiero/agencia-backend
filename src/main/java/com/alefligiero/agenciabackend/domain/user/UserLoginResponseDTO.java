package com.alefligiero.agenciabackend.domain.user;

import lombok.Builder;

@Builder
public record UserLoginResponseDTO(
        String token,
        UserResponseDTO user
) {
}
