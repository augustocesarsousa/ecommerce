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
public class UsuarioControllerPostTests {

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
    private String invalidUsuarioPerfil;

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
        invalidUsuarioPerfil = "INVALIDO";

        when(usuarioService.create(any())).thenReturn(usuarioModel);

        when(usuarioRepository.findById(existingUsuarioId)).thenReturn(Optional.of(usuarioModel));
        when(usuarioRepository.findById(notExistingUsuarioId)).thenReturn(Optional.empty());
        when(usuarioRepository.findByLogin(existingLogin)).thenReturn(usuarioModel);
        when(usuarioRepository.findByEmail(existingEmail)).thenReturn(existingUsuarioModel);
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
        usuarioDTO.setNome(shortUsuarioField);
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
        usuarioDTO.setNome(longUsuarioField);
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
        usuarioDTO.setNome(blankUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenNameIsNull() throws Exception {
        usuarioDTO.setNome(nullUsuarioField);
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
        usuarioDTO.setNome(onlyNumberUsuarioField);
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
        usuarioDTO.setLogin(shortUsuarioField);
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
        usuarioDTO.setLogin(longUsuarioField);
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
        usuarioDTO.setLogin(blankUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenLoginIsNull() throws Exception {
        usuarioDTO.setLogin(nullUsuarioField);
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
        usuarioDTO.setLogin(onlyNumberUsuarioField);
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
        usuarioDTO.setLogin(existingLogin);
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
        usuarioDTO.setSenha(shortUsuarioField);
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
        usuarioDTO.setSenha(longUsuarioField);
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
        usuarioDTO.setSenha(nullUsuarioField);
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
        usuarioDTO.setTelefone(shortUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Telefone inválido"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenInvalidEmail() throws Exception {
        usuarioDTO.setEmail(shortUsuarioField);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("E-mail inválido"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenExistingEmail() throws Exception {
        usuarioDTO.setEmail(existingEmail);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("E-mail já cadastrado"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenInvalidPerfil() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);
        jsonBody = jsonBody.replace("FINANCEIRO", invalidUsuarioPerfil);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenInvalidIdUsuarioUltAlteracao() throws Exception {
        usuarioDTO.setIdUsuarioUltAlteracao(notExistingUsuarioId);
        String jsonBody = objectMapper.writeValueAsString(usuarioDTO);

        mockMvc.perform(post("/usuarios")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Usuário para última atualização não encontrado"));
    }

}
