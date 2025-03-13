package com.ecommerce.ms_usuario.validations;

import com.ecommerce.ms_usuario.controllers.exceptions.FieldMessage;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioStatusUpdateConstraintImpl implements ConstraintValidator<UsuarioStatusUpdateConstraint, Enum> {

    List<FieldMessage> list = new ArrayList<>();

    @Override
    public void initialize(UsuarioStatusUpdateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Enum status, ConstraintValidatorContext context) {
        for (UsuarioStatus u : UsuarioStatus.values()) {
            if (u.equals(status)){
                list.add(new FieldMessage("status", "Status inválido"));
            }
        }
        for (FieldMessage fieldMessage : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
