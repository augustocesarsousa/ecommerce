package com.ecommerce.ms_empresa.services.exceptions;

import java.io.Serial;

public class EntityAlreadyRegistered extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public EntityAlreadyRegistered (String message) {
        super(message);
    }

}
