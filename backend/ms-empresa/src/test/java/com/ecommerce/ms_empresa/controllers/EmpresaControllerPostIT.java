package com.ecommerce.ms_empresa.controllers;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.factories.EmpresaFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmpresaControllerPostIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private EmpresaDTO empresaDTO;

    @BeforeEach
    void setUp() throws Exception {
        empresaDTO = EmpresaFactory.criarEmpresaValidaDTO();
    }

    @Test
    public void createShouldPersistEntityInDatabaseWhenValidDatas() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.cnpj").value(empresaDTO.getCnpj()))
                .andExpect(jsonPath("$.fantasia").value(empresaDTO.getFantasia()))
                .andExpect(jsonPath("$.dtCriacao").exists());
    }

}
