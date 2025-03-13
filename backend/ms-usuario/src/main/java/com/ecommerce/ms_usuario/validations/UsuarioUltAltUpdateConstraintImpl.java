package com.ecommerce.ms_usuario.validations;

import com.ecommerce.ms_usuario.controllers.exceptions.FieldMessage;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UsuarioUltAltUpdateConstraintImpl implements ConstraintValidator<UsuarioUltAltUpdateConstraint, UUID> {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public void initialize(UsuarioUltAltUpdateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UUID usuarioUltAlteracao, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        if(!usuarioRepository.findById(usuarioUltAlteracao).isPresent()) {
            list.add(new FieldMessage("id", "Usuário para última atualização não encontrado"));
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
