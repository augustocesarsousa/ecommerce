package com.ecommerce.ms_empresa.controllers;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.factories.EmpresaFactory;
import com.ecommerce.ms_empresa.models.EmpresaModel;
import com.ecommerce.ms_empresa.repositories.EmpresaRepository;
import com.ecommerce.ms_empresa.services.exceptions.EntityAlreadyRegistered;
import com.ecommerce.ms_empresa.services.impl.EmpresaServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(EmpresaController.class)
public class EmpresaControllerPostTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpresaServiceImpl empresaService;

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private EmpresaModel empresaModel;
    private EmpresaDTO empresaDTO;

    public EmpresaControllerPostTests() {
    }

    @BeforeEach
    void setUp() throws Exception {
        empresaModel = EmpresaFactory.criarEmpresaModel();
        empresaDTO = EmpresaFactory.criarEmpresaValidaDTO();

        when(empresaService.create(any(EmpresaDTO.class))).thenReturn(empresaModel);
    }

    @Test
    public void createShouldReturnCreatedWhenValidDatas() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void createShouldReturnConflictWhenEntityAlreadyRegistered() throws Exception {
        when(empresaRepository.count()).thenReturn(1L);
        when(empresaService.create(any(EmpresaDTO.class)))
                .thenThrow(new EntityAlreadyRegistered("Já existe uma empresa cadastrada"));

        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message")
                        .value("Já existe uma empresa cadastrada"));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenInvalidCNPJ() throws Exception {
        empresaDTO.setCnpj("123");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "cnpj"),
                                        hasEntry("message", "CNPJ inválido")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenEmptyCNPJ() throws Exception {
        empresaDTO.setCnpj("");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "cnpj"),
                                        hasEntry("message", "CNPJ é obrigatório")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenEmptyInscricaoEstadual() throws Exception {
        empresaDTO.setInscricaoEstadual("");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "inscricaoEstadual"),
                                        hasEntry("message", "Inscrição Estadual é obrigatória")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenEmptyRazaoSocial() throws Exception {
        empresaDTO.setRazaoSocial("");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "razaoSocial"),
                                        hasEntry("message", "Razao Social é obrigatória")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenInvalidTelefone() throws Exception {
        empresaDTO.setTelefone("123");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "telefone"),
                                        hasEntry("message", "Telefone inválido")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenInvalidEmail() throws Exception {
        empresaDTO.setEmail("abc");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "email"),
                                        hasEntry("message", "E-mail inválido")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenEmptyCEP() throws Exception {
        empresaDTO.setCep("");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "cep"),
                                        hasEntry("message", "CEP é obrigatório")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenEmptyLogradouro() throws Exception {
        empresaDTO.setLogradouro("");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "logradouro"),
                                        hasEntry("message", "Logradouro é obrigatório")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenEmptyNumero() throws Exception {
        empresaDTO.setNumero(null);
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "numero"),
                                        hasEntry("message", "Número é obrigatório")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenEmptyBairro() throws Exception {
        empresaDTO.setBairro("");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "bairro"),
                                        hasEntry("message", "Bairro é obrigatório")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenInvalidUF() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);
        jsonBody = jsonBody.replace("SP", "AA");

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenInvalidCpfPresidente() throws Exception {
        empresaDTO.setCpfPresidente("123");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "cpfPresidente"),
                                        hasEntry("message", "CPF do presidente inválido")
                                )
                        )
                ));
    }

    @Test
    public void createShouldReturnUnprocessableEntityWhenEmptyNomePresidente() throws Exception {
        empresaDTO.setNomePresidente("");
        String jsonBody = objectMapper.writeValueAsString(empresaDTO);

        mockMvc.perform(post("/empresas")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").value(
                        hasItem(
                                allOf(
                                        hasEntry("fieldName", "nomePresidente"),
                                        hasEntry("message", "Nome do presidente é obrigatório")
                                )
                        )
                ));
    }
}
