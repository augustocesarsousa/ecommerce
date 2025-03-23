package com.ecommerce.ms_usuario.factories;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.enums.UsuarioPerfil;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public class UsuarioFactory {

    public static UsuarioModel criarUsuarioModel() {
        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setId(UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f"));
        usuarioModel.setNome("Hal Jordan");
        usuarioModel.setLogin("lanterna.verde");
        usuarioModel.setSenha("1234");
        usuarioModel.setTelefone("11912345678");
        usuarioModel.setEmail("hal.jordan@email.com");
        usuarioModel.setStatus(UsuarioStatus.ATIVO);
        usuarioModel.setPerfil(UsuarioPerfil.FINANCEIRO);
        usuarioModel.setDtCriacao(LocalDateTime.of(2025,03,01,7,00));
        usuarioModel.setDtUltAlteracao(LocalDateTime.of(2025,03,01,7,00));
        usuarioModel.setIdUsuarioUltAlteracao(UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f"));

        return usuarioModel;
    }

    public static UsuarioModel criarExistingUsuarioModel() {
        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioModel.setId(UUID.fromString("5ef38163-8438-43f2-847b-200e5e01b78f"));
        usuarioModel.setNome("Lex Luthor");
        usuarioModel.setLogin("luthor");
        usuarioModel.setSenha("1234");
        usuarioModel.setTelefone("11912345678");
        usuarioModel.setEmail("lex.luthor@email.com");
        usuarioModel.setStatus(UsuarioStatus.INATIVO);
        usuarioModel.setPerfil(UsuarioPerfil.OPERADOR);
        usuarioModel.setDtCriacao(LocalDateTime.of(2025,03,01,7,00));
        usuarioModel.setDtUltAlteracao(LocalDateTime.of(2025,03,01,7,00));
        usuarioModel.setIdUsuarioUltAlteracao(UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f"));

        return usuarioModel;
    }

    public static UsuarioDTO criarUsuarioValidoDTO() {
        UsuarioDTO usuarioValidoDTO = new UsuarioDTO();

        BeanUtils.copyProperties(criarUsuarioModel(), usuarioValidoDTO);
        usuarioValidoDTO.setId(null);

        return usuarioValidoDTO;
    }
}
