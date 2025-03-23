package com.ecommerce.ms_usuario.controllers;

import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.services.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerGetTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioServiceImpl usuarioService;

    private UsuarioModel usuarioModel;
    private UUID existingUsuarioId;

    @BeforeEach
    void setUp() throws Exception {
        usuarioModel = UsuarioFactory.criarUsuarioModel();
        existingUsuarioId = UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f");

        when(usuarioService.findById(existingUsuarioId)).thenReturn(usuarioModel);
    }

    @Test
    public void findByIdShouldReturnUsuarioModelWhenIdExisting() throws Exception {
        mockMvc.perform(get("/usuarios/{id}", existingUsuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").exists());
    }

}
