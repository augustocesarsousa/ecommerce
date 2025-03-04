package com.ecommerce.ms_usuario.dtos;

import com.ecommerce.ms_usuario.enums.UsuarioPerfil;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import com.ecommerce.ms_usuario.models.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    private UUID id;
    private String nome;
    private String login;
    private String senha;
    private String telefone;
    private String email;
    private UsuarioStatus status;
    private UsuarioPerfil perfil;
    private LocalDateTime dtCriacao;
    private LocalDateTime dtUltAlteracao;
}
