package com.ecommerce.ms_usuario.dtos;

import com.ecommerce.ms_usuario.enums.UsuarioPerfil;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import com.ecommerce.ms_usuario.validations.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
        public static interface Cadastrar {}
        public static interface Atualizar {}
        public static interface NaoExibir {}
    }

    @JsonView(UsuarioView.NaoExibir.class)
    private UUID id;

    @JsonView({UsuarioView.Cadastrar.class, UsuarioView.Atualizar.class})
    @Size(min = 4, max = 32, message = "O nome deve ter entre 4 e 32 caracteres")
    @NotBlank(message = "O nome não pode estar em branco")
    @Pattern(regexp = "^(?!\\d+$).*$", message = "O nome não pode conter apenas números")
    private String nome;

    @JsonView(UsuarioView.Cadastrar.class)
    @Size(min = 4, max = 32, message = "O login deve ter entre 4 e 32 caracteres")
    @NotBlank(message = "O login não pode estar em branco")
    @Pattern(regexp = "^(?!\\d+$).*$", message = "O login não pode conter apenas números")
    @UsuarioLoginCreateConstraint(groups = UsuarioView.Cadastrar.class)
    private String login;

    @JsonView({UsuarioView.Cadastrar.class, UsuarioView.Atualizar.class})
    @Size(min = 4, max = 32, message = "A senha deve ter entre 4 e 32 caracteres")
    @NotBlank(groups = UsuarioView.Cadastrar.class, message = "A senha não pode estar em branco")
    private String senha;

    @JsonView({UsuarioView.Cadastrar.class, UsuarioView.Atualizar.class})
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone inválido")
    private String telefone;

    @JsonView({UsuarioView.Cadastrar.class, UsuarioView.Atualizar.class})
    @Email(message = "E-mail inválido")
    @UsuarioEmailCreateConstraint(groups = UsuarioView.Cadastrar.class)
    @UsuarioEmailUpdateConstraint(groups = UsuarioView.Atualizar.class)
    private String email;

    @JsonView(UsuarioView.Atualizar.class)
    private UsuarioStatus status;

    @JsonView({UsuarioView.Cadastrar.class, UsuarioView.Atualizar.class})
    private UsuarioPerfil perfil;

    @JsonView(UsuarioView.NaoExibir.class)
    private LocalDateTime dtCriacao;

    @JsonView(UsuarioView.NaoExibir.class)
    private LocalDateTime dtUltAlteracao;

    @JsonView({UsuarioView.Cadastrar.class, UsuarioView.Atualizar.class})
    @IdUsuarioUltAltConstraint(groups = {UsuarioView.Cadastrar.class, UsuarioView.Atualizar.class})
    private UUID idUsuarioUltAlteracao;
}
