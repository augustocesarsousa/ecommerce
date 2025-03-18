package com.ecommerce.ms_usuario.validations;

import com.ecommerce.ms_usuario.controllers.exceptions.FieldMessage;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IdUsuarioUltAltConstraintImpl implements ConstraintValidator<IdUsuarioUltAltConstraint, UUID> {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public void initialize(IdUsuarioUltAltConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UUID idUsuarioUltAlteracao, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        if(!usuarioRepository.findById(idUsuarioUltAlteracao).isPresent()) {
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
