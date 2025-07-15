package com.ecommerce.ms_empresa.services.impl;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.factories.EmpresaFactory;
import com.ecommerce.ms_empresa.models.EmpresaModel;
import com.ecommerce.ms_empresa.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class EmpresaServiceImplIT {

    @Autowired
    private EmpresaServiceImpl empresaService;

    private EmpresaDTO empresaDTO;
    private UUID notExistingId;

    @BeforeEach
    void setUp() throws Exception {
        empresaDTO = EmpresaFactory.criarEmpresaValidaDTO();
        notExistingId = UUID.fromString("4ef38163-8438-43f2-847b-200e5e01b78f");
    }

    @Test
    public void createShouldPersistEntityInDatabaseWhenValidDatas() {
        EmpresaModel empresaModel = empresaService.create(empresaDTO);

        Assertions.assertNotNull(empresaModel.getId());
        Assertions.assertNotNull(empresaModel.getDtCriacao());
    }

    @Test
    public void updateShouldUpdateEntityInDatabaseWhenValidDatas() {
        EmpresaModel empresaModel = empresaService.create(empresaDTO);

        empresaDTO.setFantasia("Lutor Corp");
        empresaModel = empresaService.update(empresaModel.getId(), empresaDTO);

        Assertions.assertEquals("Lutor Corp", empresaModel.getFantasia());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenNotExistingId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            empresaService.update(notExistingId, empresaDTO);
        });
    }

    @Test
    public void findOneShouldReturnEntityWhenRegisteredEntity() {
        empresaService.create(empresaDTO);

        EmpresaModel empresaModel = empresaService.findOne();

        Assertions.assertNotNull(empresaModel.getId());
        Assertions.assertNotNull(empresaModel.getDtCriacao());
    }

    @Test
    public void findOneShouldReturnEmptyEntityWhenNotRegisteredEntity() {
        EmpresaModel empresaModel = empresaService.findOne();

        Assertions.assertNull(empresaModel.getId());
        Assertions.assertNull(empresaModel.getDtCriacao());
    }
}
