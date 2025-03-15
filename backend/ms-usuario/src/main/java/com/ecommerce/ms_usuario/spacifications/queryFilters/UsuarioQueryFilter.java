package com.ecommerce.ms_usuario.spacifications.queryFilters;

import com.ecommerce.ms_usuario.enums.UsuarioPerfil;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import static com.ecommerce.ms_usuario.spacifications.UsuarioSpecification.*;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

@Data
public class UsuarioQueryFilter {

    private String nome;
    private String login;
    private String telefone;
    private String email;
    private UsuarioStatus status;
    private UsuarioPerfil perfil;

    public Specification<UsuarioModel> toSpecification() {
        return nomeLikeIgnoreCase(nome)
                .and(loginLikeIgnoreCase(login))
                .and(telefoneEquals(telefone))
                .and(emailLikeIgnoreCase(email))
                .and(statusEquals(status))
                .and(perfilEquals(perfil));
    }
}
