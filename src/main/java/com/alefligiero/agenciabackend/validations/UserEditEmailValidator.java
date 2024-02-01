package com.alefligiero.agenciabackend.validations;

import com.alefligiero.agenciabackend.controllers.exceptions.FieldMessage;
import com.alefligiero.agenciabackend.domain.user.User;
import com.alefligiero.agenciabackend.domain.user.UserEditEmailDTO;
import com.alefligiero.agenciabackend.repositories.UserRepository;
import com.alefligiero.agenciabackend.services.exceptions.ResourceNotFoundException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserEditEmailValidator implements ConstraintValidator<UserEditEmailValid, UserEditEmailDTO> {

    private final UserRepository repository;

    @Override
    public boolean isValid(UserEditEmailDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        User entity = repository.findByEmailIgnoreCase(dto.oldEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found, email: " + dto.oldEmail()));

        verifyIfEqualEmails(entity.getEmail(), dto.newEmail(), list);
        verifyIfEmailExists(dto.newEmail(), list);

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }

    private void verifyIfEqualEmails(String oldEmail, String newEmail, List<FieldMessage> list) {
        if (oldEmail.equalsIgnoreCase(newEmail)) {
            list.add(new FieldMessage("newEmail", "The new email cannot be the same as the old email"));
        }
    }

    private void verifyIfEmailExists(String newEmail, List<FieldMessage> list) {
        if (repository.existsUserByEmailIgnoreCase(newEmail)) {
            list.add(new FieldMessage("newEmail", "This email is already in use"));
        }
    }
}
