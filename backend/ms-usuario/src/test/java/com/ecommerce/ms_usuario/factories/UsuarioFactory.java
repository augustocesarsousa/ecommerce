package com.ecommerce.ms_usuario.factories;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.enums.UsuarioPerfil;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import org.springframework.beans.BeanUtils;

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
        usuarioModel.setPerfil(UsuarioPerfil.FINANCEIRO);
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
