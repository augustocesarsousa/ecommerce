package com.ecommerce.ms_usuario.services;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO create(UsuarioDTO usuarioDTO);

    UsuarioDTO update(UsuarioDTO usuarioDTO);
}
