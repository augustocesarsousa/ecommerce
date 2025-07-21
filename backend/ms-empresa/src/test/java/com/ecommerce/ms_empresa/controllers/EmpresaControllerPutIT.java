package com.ecommerce.ms_empresa.controllers;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.factories.EmpresaFactory;
import com.ecommerce.ms_empresa.models.EmpresaModel;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmpresaControllerPutIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private EmpresaDTO empresaDTO;
    private UUID notExistingId;

    @BeforeEach
    void setUp() throws Exception {
        empresaDTO = EmpresaFactory.criarEmpresaValidaDTO();
        notExistingId = UUID.fromString("4ef38163-8438-43f2-847b-200e5e01b78f");
    }

    @Test
    public void updateShouldUpdateEntityInDatabaseWhenValidDatas() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        MvcResult postResult = mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = postResult.getResponse().getContentAsString();
        EmpresaModel createdEmpresa = objectMapper.readValue(responseBody, EmpresaModel.class);
        UUID createdEmpresaId = createdEmpresa.getId();

        empresaDTO.setRazaoSocial("Lutor Corp");
        jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(put("/empresas/{id}", createdEmpresaId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.razaoSocial").value(empresaDTO.getRazaoSocial()));
    }

    @Test
    public void updateShouldReturnNotFoundWhenNotExistingId() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(put("/empresas/{id}", notExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
