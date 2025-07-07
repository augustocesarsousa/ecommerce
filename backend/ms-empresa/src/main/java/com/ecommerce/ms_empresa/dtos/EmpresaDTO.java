package com.ecommerce.ms_empresa.dtos;

import com.ecommerce.ms_empresa.enums.UnidadeFederativa;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpresaDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String cnpj;

    private String inscricaoEstadual;

    private String razaoSocial;

    private String fantasia;

    private String telefone;

    private String email;

    private String cep;

    private String logradouro;

    private Integer numero;

    private String complemento;

    private String bairro;

    private UnidadeFederativa uf;

    private String cpfPresidente;

    private String nomePresidente;

    private LocalDateTime dtCriacao;

    private LocalDateTime dtUltAlteracao;

    private UUID idUsuarioUltAlteracao;
}
