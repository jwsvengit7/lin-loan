package com.lin.commons.validations;

import jakarta.validation.*;

public class ValidateUserFields implements ConstraintValidator<UserFields,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !s.isEmpty() || !s.isBlank();
    }
}
