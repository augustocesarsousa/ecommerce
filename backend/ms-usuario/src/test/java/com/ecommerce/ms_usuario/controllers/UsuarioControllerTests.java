package com.ecommerce.ms_usuario.controllers;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import com.ecommerce.ms_usuario.services.impl.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioServiceImpl usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioDTO usuarioDTO;
    private UsuarioModel usuarioModel;
    private UUID existingID;

    @BeforeEach
    void setUp() throws Exception {
        usuarioDTO = UsuarioFactory.criarUsuarioValidoDTO();
        usuarioModel = UsuarioFactory.criarUsuarioModel();
        existingID = UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f");

        when(usuarioService.create(any())).thenReturn(usuarioModel);

        when(usuarioRepository.findById(existingID)).thenReturn(Optional.of(usuarioModel));
    }

    @Test
    public void createShouldReturnCreatedWhenValidDatas() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dtCriacao").exists());
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenNameSizeIsThree() throws Exception {
        usuarioDTO.setNome("abc");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("O nome deve ter entre 4 e 32 caracteres"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenNameSizeIsThirtyThree() throws Exception {
        usuarioDTO.setNome("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("O nome deve ter entre 4 e 32 caracteres"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenNameIsBlank() throws Exception {
        usuarioDTO.setNome("");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[1].message")
                        .value("O nome não pode estar em branco"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenNameIsNull() throws Exception {
        usuarioDTO.setNome(null);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("O nome não pode estar em branco"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenNameHasOnlyNumbers() throws Exception {
        usuarioDTO.setNome("1234");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("O nome não pode conter apenas números"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenLoginSizeIsThree() throws Exception {
        usuarioDTO.setLogin("abc");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("O login deve ter entre 4 e 32 caracteres"));
    }
}
