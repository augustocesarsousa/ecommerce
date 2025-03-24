package com.ecommerce.ms_usuario.controllers;

import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.services.exceptions.ResourceNotFoundException;
import com.ecommerce.ms_usuario.services.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
    private UUID notExistingUsuarioId;

    private PageImpl<UsuarioModel> page;

    @BeforeEach
    void setUp() throws Exception {
        usuarioModel = UsuarioFactory.criarUsuarioModel();
        existingUsuarioId = UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f");
        notExistingUsuarioId = UUID.fromString("9ef38163-8438-43f2-847b-200e5e01b78f");
        page = new PageImpl<>(List.of(usuarioModel));

        when(usuarioService.findById(existingUsuarioId)).thenReturn(usuarioModel);
        when(usuarioService.findById(notExistingUsuarioId)).thenThrow(ResourceNotFoundException.class);

        when(usuarioService.findAll(any(), any())).thenReturn(page);
    }

    @Test
    public void findByIdShouldReturnUsuarioModelWhenIdExisting() throws Exception {
        mockMvc.perform(get("/usuarios/{id}", existingUsuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").exists());
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExisting() throws Exception {
        mockMvc.perform(get("/usuarios/{id}", notExistingUsuarioId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAllShouldReturnPage() throws Exception {
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk());
    }

}
