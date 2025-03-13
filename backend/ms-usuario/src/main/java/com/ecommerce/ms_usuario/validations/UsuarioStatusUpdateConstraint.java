package com.ecommerce.ms_usuario.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsuarioStatusUpdateConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsuarioStatusUpdateConstraint {
    String message() default "Invalid status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
