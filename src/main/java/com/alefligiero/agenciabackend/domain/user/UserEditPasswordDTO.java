package com.alefligiero.agenciabackend.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UserEditPasswordDTO(
        @NotBlank(message = "Campo obrigatório")
        String oldPassword,
        @NotBlank(message = "Campo obrigatório")
        String newPassword
) {
}
