package com.ecommerce.ms_usuario.controllers;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.ecommerce.ms_usuario.services.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UsuarioControllerPostIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioDTO usuarioValidoDTO;

    @BeforeEach
    void setUp() throws Exception {
        usuarioValidoDTO = UsuarioFactory.criarUsuarioValidoDTO();

        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(UsuarioDTO.UsuarioView.Cadastrar.class));
    }

    @Test
    public void createShouldPersistEntityInDatabaseWhenValidDatas() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(usuarioValidoDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value(usuarioValidoDTO.getNome()))
                .andExpect(jsonPath("$.login").value(usuarioValidoDTO.getLogin()))
                .andExpect(jsonPath("$.telefone").value(usuarioValidoDTO.getTelefone()))
                .andExpect(jsonPath("$.email").value(usuarioValidoDTO.getEmail()));
    }
}
