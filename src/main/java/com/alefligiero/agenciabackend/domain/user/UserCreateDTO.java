package com.alefligiero.agenciabackend.domain.user;

import com.alefligiero.agenciabackend.domain.role.RoleRequestDTO;
import com.alefligiero.agenciabackend.validations.UserCreateValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;

@UserCreateValid
@Builder
public record UserCreateDTO(
        @NotBlank(message = "Campo obrigatório")
        String firstName,
        @NotBlank(message = "Campo obrigatório")
        String lastName,
        @NotBlank(message = "Campo obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 8, message = "Deve ter no mínimo 8 caracteres")
        String password,
        Set<RoleRequestDTO> roles
) {
}
