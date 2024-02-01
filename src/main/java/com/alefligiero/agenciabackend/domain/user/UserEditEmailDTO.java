package com.alefligiero.agenciabackend.domain.user;

import com.alefligiero.agenciabackend.validations.UserEditEmailValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@UserEditEmailValid
public record UserEditEmailDTO(
        @NotBlank(message = "Old email is required")
        @Email(message = "Invalid email")
        String oldEmail,
        @NotBlank(message = "New email is required")
        @Email(message = "Invalid email")
        String newEmail

) {
}
