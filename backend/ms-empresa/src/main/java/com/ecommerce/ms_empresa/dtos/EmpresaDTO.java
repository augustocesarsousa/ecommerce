package com.ecommerce.ms_empresa.dtos;

import com.ecommerce.ms_empresa.enums.UnidadeFederativa;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
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

    public interface EmpresaView {
        public static interface Cadastrar {}
        public static interface Atualizar {}
        public static interface NaoExibir {}
    }

    @JsonView({
            EmpresaView.NaoExibir.class
    })
    private UUID id;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String cnpj;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String inscricaoEstadual;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String razaoSocial;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String fantasia;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String telefone;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String email;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String cep;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String logradouro;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private Integer numero;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String complemento;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String bairro;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private UnidadeFederativa uf;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String cpfPresidente;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private String nomePresidente;

    @JsonView({
            EmpresaView.NaoExibir.class
    })
    private LocalDateTime dtCriacao;

    @JsonView({
            EmpresaView.NaoExibir.class
    })
    private LocalDateTime dtUltAlteracao;

    @JsonView({
            EmpresaView.Cadastrar.class,
            EmpresaView.Atualizar.class
    })
    private UUID idUsuarioUltAlteracao;
}
