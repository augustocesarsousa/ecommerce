package com.ecommerce.ms_usuario.services;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.models.UsuarioModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {
    UsuarioModel create(UsuarioDTO usuarioDTO);

    UsuarioModel update(UsuarioDTO usuarioDTO);

    UsuarioModel findById(UUID id);

    List<UsuarioModel> findAll();

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}