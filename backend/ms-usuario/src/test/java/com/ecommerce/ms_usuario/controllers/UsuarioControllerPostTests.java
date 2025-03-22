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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerPostTests {

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
    private UUID notExistingId;
    private String existingLogin;

    @BeforeEach
    void setUp() throws Exception {
        usuarioDTO = UsuarioFactory.criarUsuarioValidoDTO();
        usuarioModel = UsuarioFactory.criarUsuarioModel();
        existingID = UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f");
        notExistingId = UUID.fromString("1ef38163-8438-43f2-847b-200e5e01b78f");
        existingLogin = "batman";

        when(usuarioService.create(any())).thenReturn(usuarioModel);

        when(usuarioRepository.findById(existingID)).thenReturn(Optional.of(usuarioModel));
        when(usuarioRepository.findByLogin(existingLogin)).thenReturn(usuarioModel);
    }

    @Test
    public void createShouldReturnCreatedWhenValidDatas() throws Exception {
        objectMapper.setConfig(objectMapper.getSerializationConfig().withView(UsuarioDTO.UsuarioView.Cadastrar.class));
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
                .andExpect(status().isUnprocessableEntity());
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
                        .value("Nome não informado"));
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

    @Test
    public void createShouldReturnUnprocessableEntityWhenLoginSizeIsThirtyThree() throws Exception {
        usuarioDTO.setLogin("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("O login deve ter entre 4 e 32 caracteres"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenLoginIsBlank() throws Exception {
        usuarioDTO.setLogin("");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenLoginIsNull() throws Exception {
        usuarioDTO.setLogin(null);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Login não informado"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenLoginHasOnlyNumbers() throws Exception {
        usuarioDTO.setLogin("1234");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("O login não pode conter apenas números"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenLoginAlreadyExisting() throws Exception {
        usuarioDTO.setLogin("batman");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Login já cadastrado"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenSenhaSizeIsThree() throws Exception {
        usuarioDTO.setSenha("123");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("A senha deve ter entre 4 e 32 caracteres"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenSenhaSizeIsThirtyThree() throws Exception {
        usuarioDTO.setSenha("123456789012345678901234567890123");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("A senha deve ter entre 4 e 32 caracteres"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenSenhaIsNull() throws Exception {
        usuarioDTO.setSenha(null);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("A senha não pode estar em branco"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenInvalidTelefone() throws Exception {
        usuarioDTO.setTelefone("123");
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Telefone inválido"));
    }

}
