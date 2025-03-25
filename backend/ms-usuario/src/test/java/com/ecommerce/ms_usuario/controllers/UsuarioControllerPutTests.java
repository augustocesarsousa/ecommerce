package com.ecommerce.ms_usuario.controllers;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import com.ecommerce.ms_usuario.services.exceptions.ResourceNotFoundException;
import com.ecommerce.ms_usuario.services.impl.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerPutTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioServiceImpl usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioModel usuarioModel;
    private UsuarioModel existingUsuarioModel;
    private UsuarioDTO usuarioDTO;
    private UUID existingUsuarioId;
    private UUID notExistingUsuarioId;
    private String shortUsuarioField;
    private String longUsuarioField;
    private String onlyNumberUsuarioField;
    private String blankUsuarioField;
    private String nullUsuarioField;
    private String existingLogin;
    private String existingEmail;

    @BeforeEach
    void setUp() throws Exception {
        usuarioDTO = UsuarioFactory.criarUsuarioValidoDTO();
        usuarioModel = UsuarioFactory.criarUsuarioModel();
        existingUsuarioModel = UsuarioFactory.criarExistingUsuarioModel();
        existingUsuarioId = UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f");
        notExistingUsuarioId = UUID.fromString("1ef38163-8438-43f2-847b-200e5e01b78f");
        shortUsuarioField = "abc";
        longUsuarioField = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        onlyNumberUsuarioField = "1234";
        blankUsuarioField = "";
        nullUsuarioField = null;
        existingLogin = "batman";
        existingEmail = "lex.luthor@email.com";

        when(usuarioService.update(eq(existingUsuarioId), any())).thenReturn(usuarioModel);
        when(usuarioService.update(eq(notExistingUsuarioId), any())).thenThrow(ResourceNotFoundException.class);

        when(usuarioRepository.findById(existingUsuarioId)).thenReturn(Optional.of(usuarioModel));
        when(usuarioRepository.findByLogin(existingLogin)).thenReturn(usuarioModel);
        when(usuarioRepository.findByEmail(existingEmail)).thenReturn(existingUsuarioModel);
    }

    @Test
    public void updateShouldReturnOkWhenValidDatas() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuarioUltAlteracao").exists());
    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExisting() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", notExistingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnUnprocessableEntityWhenNameSizeIsThree() throws Exception {
        usuarioDTO.setNome(shortUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("O nome deve ter entre 4 e 32 caracteres"));
    }

    @Test
    public void updateShouldReturnUnprocessableEntityWhenNameSizeIsThirtyThree() throws Exception {
        usuarioDTO.setNome(longUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("O nome deve ter entre 4 e 32 caracteres"));
    }

    @Test
    public void updateShouldReturnUnprocessableEntityWhenNameIsBlank() throws Exception {
        usuarioDTO.setNome(blankUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void updateShouldReturnUnprocessableEntityWhenNameIsNull() throws Exception {
        usuarioDTO.setNome(nullUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Nome não informado"));
    }

    @Test
    public void updateShouldReturnUnprocessableEntityWhenNameHasOnlyNumbers() throws Exception {
        usuarioDTO.setNome(onlyNumberUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("O nome não pode conter apenas números"));
    }

    @Test
    public void updateShouldReturnUnprocessableEntityWhenSenhaSizeIsThree() throws Exception {
        usuarioDTO.setSenha(shortUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("A senha deve ter entre 4 e 32 caracteres"));
    }

    @Test
    public void updateShouldReturnUnprocessableEntityWhenSenhaSizeIsThirtyThree() throws Exception {
        usuarioDTO.setSenha(longUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("A senha deve ter entre 4 e 32 caracteres"));
    }

    @Test
    public void updateShouldReturnUnprocessableEntityWhenInvalidTelefone() throws Exception {
        usuarioDTO.setTelefone(shortUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Telefone inválido"));
    }

    @Test
    public void updateShouldReturnUnprocessableEntityWhenInvalidEmail() throws Exception {
        usuarioDTO.setEmail(shortUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("E-mail inválido"));
    }

    @Test
    public void updateShouldReturnUnprocessableEntityWhenExistingEmail() throws Exception {
        usuarioDTO.setEmail(existingEmail);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(put("/usuarios/{id}", existingUsuarioId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("E-mail já cadastrado"));
    }
}
