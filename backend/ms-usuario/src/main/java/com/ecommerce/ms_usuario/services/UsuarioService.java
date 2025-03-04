package com.ecommerce.ms_usuario.services;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.models.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {
    UsuarioDTO create(UsuarioDTO usuarioDTO);

    UsuarioDTO update(UsuarioDTO usuarioDTO);

    UsuarioDTO findById(UUID id);

    List<Usuario> findAll();
}
