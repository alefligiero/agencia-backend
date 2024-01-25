package com.alefligiero.agenciabackend.controllers.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

}
