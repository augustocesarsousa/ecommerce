package com.ecommerce.ms_empresa.controllers;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.factories.EmpresaFactory;
import com.ecommerce.ms_empresa.models.EmpresaModel;
import com.ecommerce.ms_empresa.repositories.EmpresaRepository;
import com.ecommerce.ms_empresa.services.impl.EmpresaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(EmpresaController.class)
public class EmpresaControllerGetTestes {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaServiceImpl empresaService;

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private EmpresaModel empresaModel;

    @BeforeEach
    void setUp() throws Exception {
        empresaModel = EmpresaFactory.criarEmpresaModel();
    }

    @Test
    public void findOneShouldReturnEntityWhenHasEntityRegistered() throws Exception {
        when(empresaService.findOne()).thenReturn(empresaModel);

        mockMvc.perform(get("/empresas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cnpj").value(empresaModel.getCnpj()))
                .andExpect(jsonPath("$.razaoSocial").value(empresaModel.getRazaoSocial()));
    }

    @Test
    public void findOneShouldReturnNullWhenHasNotEntityRegistered() throws Exception {
        when(empresaService.findOne()).thenReturn(new EmpresaModel());

        mockMvc.perform(get("/empresas"))
                .andExpect(status().isOk())
                .andExpect(content().json("{}"));
    }
}
