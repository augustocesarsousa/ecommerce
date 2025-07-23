package com.ecommerce.ms_empresa.services;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.models.EmpresaModel;

import java.util.UUID;

public interface EmpresaService {
    EmpresaModel create(EmpresaDTO empresaDTO);

    EmpresaModel findOne();

    EmpresaModel update(UUID id, EmpresaDTO empresaDTO);
}
