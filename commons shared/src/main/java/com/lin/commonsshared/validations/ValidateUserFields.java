package com.lin.commonsshared.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateUserFields implements ConstraintValidator<UserFields,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !s.isEmpty() || !s.isBlank();
    }
}
