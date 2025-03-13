package com.ecommerce.ms_usuario.validations;

import com.ecommerce.ms_usuario.controllers.exceptions.FieldMessage;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UsuarioEmailCreateConstraintImpl implements ConstraintValidator<UsuarioEmailCreateConstraint, String> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void initialize(UsuarioEmailCreateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        UsuarioModel usuarioModel = usuarioRepository.findByEmail(email);
        if(usuarioModel != null) {
            list.add(new FieldMessage("email", "E-mail já cadastrado"));
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
