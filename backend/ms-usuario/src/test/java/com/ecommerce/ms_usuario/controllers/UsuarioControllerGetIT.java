package com.ecommerce.ms_usuario.controllers;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UsuarioControllerGetIT {

    @Autowired
    private MockMvc mockMvc;

    private UUID existingUsuarioId;
    private UUID notExistingUsuarioId;

    @BeforeEach
    void setUp() throws Exception {
        existingUsuarioId = UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f");
        notExistingUsuarioId = UUID.fromString("9ef38163-8438-43f2-847b-200e5e01b78f");
    }

    @Test
    public void findByIdShouldReturnEntityFromDatabaseWhenExistingId() throws Exception {
        mockMvc.perform(get("/usuarios/{id}", existingUsuarioId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Bruce Wayne"))
                .andExpect(jsonPath("$.telefone").value("11912345678"))
                .andExpect(jsonPath("$.email").value("bruce.wayne@email.com"));
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenNotExistingId() throws Exception {
        mockMvc.perform(get("/usuarios/{id}", notExistingUsuarioId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
