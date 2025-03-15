package com.ecommerce.ms_usuario.spacifications;

import com.ecommerce.ms_usuario.enums.UsuarioPerfil;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class UsuarioSpecification {

    public static Specification<UsuarioModel> nomeLikeIgnoreCase(String nome) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(nome)) {
                return null;
            }
            return builder.like(builder.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<UsuarioModel> loginLikeIgnoreCase(String login) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(login)) {
                return null;
            }
            return builder.like(builder.lower(root.get("login")), "%" + login.toLowerCase() + "%");
        };
    }

    public static Specification<UsuarioModel> telefoneEquals(String telefone) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(telefone)) {
                return null;
            }
            return builder.equal(root.get("telefone"), telefone);
        };
    }

    public static Specification<UsuarioModel> emailLikeIgnoreCase(String email) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(email)) {
                return null;
            }
            return builder.like(builder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    public static Specification<UsuarioModel> statusEquals(UsuarioStatus status) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(status)) {
                return null;
            }
            return builder.equal(root.get("status"), status);
        };
    }

    public static Specification<UsuarioModel> perfilEquals(UsuarioPerfil perfil) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(perfil)) {
                return null;
            }
            return builder.equal(root.get("perfil"), perfil);
        };
    }
}
