package com.ecommerce.ms_usuario.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsuarioLoginCreateConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsuarioLoginCreateConstraint {
    String message() default "Invalid login";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
