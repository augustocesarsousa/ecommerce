package com.ecommerce.ms_usuario.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsuarioUltAltUpdateConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsuarioUltAltUpdateConstraint {
    String message() default "Invalid last user update";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
