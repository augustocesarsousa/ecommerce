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
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmpresaControllerGetIT {

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
    public void findOneShouldReturnEmptyEntityWhenHasNoEntityRegistered() throws Exception {
        mockMvc.perform(get("/empresas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isEmpty())
                .andExpect(jsonPath("$.cnpj").isEmpty())
                .andExpect(jsonPath("$.razaoSocial").isEmpty());
    }

    @Test
    public void findOneShouldReturnEntityWhenHasEntityRegistered() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/empresas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.cnpj").value(empresaDTO.getCnpj()))
                .andExpect(jsonPath("$.razaoSocial").value(empresaDTO.getRazaoSocial()))
                .andExpect(jsonPath("$.dtCriacao").exists());
    }
}
