package com.ecommerce.ms_usuario.services;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {
    UsuarioDTO create(UsuarioDTO usuarioDTO);

    UsuarioDTO update(UsuarioDTO usuarioDTO);

    UsuarioDTO findById(UUID id);
}
