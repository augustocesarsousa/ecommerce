package com.ecommerce.ms_usuario.services.exceptions;

import java.io.Serial;

public class ExistingAttributeException  extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ExistingAttributeException (String message) {
        super(message);
    }
}
