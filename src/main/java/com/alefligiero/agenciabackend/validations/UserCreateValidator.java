package com.alefligiero.agenciabackend.validations;

import com.alefligiero.agenciabackend.controllers.exceptions.FieldMessage;
import com.alefligiero.agenciabackend.domain.user.UserCreateDTO;
import com.alefligiero.agenciabackend.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserCreateValidator implements ConstraintValidator<UserCreateValid, UserCreateDTO> {

    private final UserRepository repository;

    @Override
    public boolean isValid(UserCreateDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();
        System.out.println("HOPE");

        if (repository.existsUserByEmailIgnoreCase(dto.email())) {
            list.add(new FieldMessage("email", "Email já existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }
}