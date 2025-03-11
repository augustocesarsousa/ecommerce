package com.ecommerce.ms_usuario.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class FieldMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fieldName;
    private String message;
}
