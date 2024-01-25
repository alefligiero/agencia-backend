package com.alefligiero.agenciabackend.domain.user;

public record UserEditPasswordDTO(
        String oldPassword,
        String newPassword
) {
}
