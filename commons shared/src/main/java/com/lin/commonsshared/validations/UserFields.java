package com.lin.commonsshared.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidateUserFields.class)
public @interface UserFields {
    String message()  default "Input cannot be blank or empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
