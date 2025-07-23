package com.ecommerce.ms_empresa.services.impl;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.factories.EmpresaFactory;
import com.ecommerce.ms_empresa.models.EmpresaModel;
import com.ecommerce.ms_empresa.repositories.EmpresaRepository;
import com.ecommerce.ms_empresa.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class EmpresaServiceImpTests {

    @InjectMocks
    EmpresaServiceImpl empresaService;

    @Mock
    EmpresaRepository empresaRepository;

    private EmpresaDTO empresaValidaDTO;
    private UUID existingId;
    private UUID notExistingId;

    @BeforeEach
    void setUp() throws Exception {
        EmpresaModel empresaModel = EmpresaFactory.criarEmpresaModel();
        empresaValidaDTO = EmpresaFactory.criarEmpresaValidaDTO();
        existingId = UUID.fromString("3ef38163-8438-43f2-847b-200e5e01b78f");
        notExistingId = UUID.fromString("4ef38163-8438-43f2-847b-200e5e01b78f");

        when(empresaRepository.save(any())).thenReturn(empresaModel);
        when(empresaRepository.findById(existingId)).thenReturn(Optional.of(empresaModel));
        when(empresaRepository.findFirstByOrderByIdAsc()).thenReturn(Optional.of(empresaModel));
    }

    @Test
    public void createShouldPersistEntityWhenValidDatas() {
        EmpresaModel empresaModel = empresaService.create(empresaValidaDTO);

        Assertions.assertNotNull(empresaModel.getId());
    }

    @Test
    public void updateShouldPersistEntityWhenValidDatas() {
        EmpresaModel empresaModel = empresaService.update(existingId, empresaValidaDTO);

        Assertions.assertNotNull(empresaModel);
        Assertions.assertEquals(empresaModel.getId(), existingId);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenNotExistingId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            empresaService.update(notExistingId, empresaValidaDTO);
        });
    }

    @Test
    public void findOneShouldReturnEntityWhenEntityExisting() {
        Assertions.assertNotNull(empresaService.findOne().getId());
    }
}
