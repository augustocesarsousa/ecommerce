package com.ecommerce.ms_usuario.controllers;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UsuarioControllerPutIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioDTO usuarioValidoDTO;
    private UUID existingUsuarioId;
    private UUID notExistingUsuarioId;

    @BeforeEach
    void setUp() throws Exception {
        usuarioValidoDTO = UsuarioFactory.criarUsuarioValidoDTO();
        existingUsuarioId = UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f");
        notExistingUsuarioId = UUID.fromString("9ef38163-8438-43f2-847b-200e5e01b78f");

        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(UsuarioDTO.UsuarioView.Atualizar.class));
    }

    @Test
    public void updateShouldUpdateEntityInDatabaseWhenValidDatas() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(usuarioValidoDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value(usuarioValidoDTO.getNome()))
                .andExpect(jsonPath("$.telefone").value(usuarioValidoDTO.getTelefone()))
                .andExpect(jsonPath("$.email").value(usuarioValidoDTO.getEmail()));
    }

    @Test
    public void updateShouldReturnNotFoundWhenNotExistingId() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(usuarioValidoDTO);

        mockMvc.perform(put("/usuarios/{id}", notExistingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
