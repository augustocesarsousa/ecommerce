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

public class UsuarioEmailUpdateConstraintImpl implements ConstraintValidator<UsuarioEmailUpdateConstraint, String> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void initialize(UsuarioEmailUpdateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String usuarioEmail, ConstraintValidatorContext context) {

        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String usuarioId = uriVars.get("id");
        List<FieldMessage> list = new ArrayList<>();
        UsuarioModel usuarioModel = usuarioRepository.findByEmail(usuarioEmail);

        if(usuarioModel != null && !usuarioId.equals(usuarioModel.getId())) {
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
