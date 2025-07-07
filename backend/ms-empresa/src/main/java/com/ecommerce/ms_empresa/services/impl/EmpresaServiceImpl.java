package com.ecommerce.ms_empresa.services.impl;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.models.EmpresaModel;
import com.ecommerce.ms_empresa.repositories.EmpresaRepository;
import com.ecommerce.ms_empresa.services.EmpresaService;
import com.ecommerce.ms_empresa.services.exceptions.EntityAlreadyRegistered;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    EmpresaRepository empresaRepository;

    @Override
    public EmpresaModel create(EmpresaDTO empresaDTO) {
        if(empresaRepository.count() > 0) {
            throw new EntityAlreadyRegistered("Já existe uma empresa cadastrada");
        }

        EmpresaModel empresaModel = new EmpresaModel();

        empresaDTO.setDtCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        empresaDTO.setDtUltAlteracao(empresaDTO.getDtCriacao());

        BeanUtils.copyProperties(empresaDTO, empresaModel);

        return empresaRepository.save(empresaModel);
    }

    @Override
    public EmpresaModel findOne() {
        return null;
    }

    @Override
    public EmpresaModel update(UUID id, EmpresaDTO empresaDTO) {
        return null;
    }
}
