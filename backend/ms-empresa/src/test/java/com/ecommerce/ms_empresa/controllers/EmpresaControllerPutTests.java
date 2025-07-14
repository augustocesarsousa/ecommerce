package com.ecommerce.ms_empresa.controllers;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.factories.EmpresaFactory;
import com.ecommerce.ms_empresa.models.EmpresaModel;
import com.ecommerce.ms_empresa.repositories.EmpresaRepository;
import com.ecommerce.ms_empresa.services.exceptions.ResourceNotFoundException;
import com.ecommerce.ms_empresa.services.impl.EmpresaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(EmpresaController.class)
public class EmpresaControllerPutTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaServiceImpl empresaService;

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private EmpresaModel empresaModel;
    private EmpresaDTO empresaDTO;
    private UUID existingEmpresaId;
    private UUID notExistingEmpresaId;

    @BeforeEach
    void setUp() throws Exception {
        empresaModel = EmpresaFactory.criarEmpresaModel();
        empresaDTO = EmpresaFactory.criarEmpresaValidaDTO();
        existingEmpresaId = UUID.fromString("3ef38163-8438-43f2-847b-200e5e01b78f");
        notExistingEmpresaId = UUID.fromString("4ef38163-8438-43f2-847b-200e5e01b78f");

        when(empresaService.update(eq(existingEmpresaId), any(EmpresaDTO.class)))
                .thenReturn(empresaModel);
        when(empresaService.update(eq(notExistingEmpresaId), any(EmpresaDTO.class)))
                .thenThrow(ResourceNotFoundException.class);
    }

    @Test
    public void updateShouldReturnOkWhenValidDatas() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(put("/empresas/{id}", existingEmpresaId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.cnpj").value(empresaDTO.getCnpj()))
                .andExpect(jsonPath("$.razaoSocial").value(empresaDTO.getRazaoSocial()))
                .andExpect(jsonPath("$.email").value(empresaDTO.getEmail()));
    }

    @Test
    public void updateShouldReturnNotFoundWhenNotExistingId() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(put("/empresas/{id}", notExistingEmpresaId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
