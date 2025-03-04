package com.ecommerce.ms_usuario.dtos;

import com.ecommerce.ms_usuario.enums.UsuarioPerfil;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public interface UsuarioView {
        public static interface Cadastro {}
        public static interface Atualizar {}
    }

    @JsonView(UsuarioView.Atualizar.class)
    private UUID id;

    @JsonView({UsuarioView.Cadastro.class, UsuarioView.Atualizar.class})
    private String nome;

    @JsonView(UsuarioView.Cadastro.class)
    private String login;

    @JsonView({UsuarioView.Cadastro.class, UsuarioView.Atualizar.class})
    private String senha;

    @JsonView({UsuarioView.Cadastro.class, UsuarioView.Atualizar.class})
    private String telefone;

    @JsonView({UsuarioView.Cadastro.class, UsuarioView.Atualizar.class})
    private String email;

    @JsonView(UsuarioView.Atualizar.class)
    private UsuarioStatus status;

    @JsonView({UsuarioView.Cadastro.class, UsuarioView.Atualizar.class})
    private UsuarioPerfil perfil;

    private LocalDateTime dtCriacao;

    private LocalDateTime dtUltAlteracao;
}
