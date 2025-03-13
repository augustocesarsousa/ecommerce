package com.ecommerce.ms_usuario.services;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UsuarioService {
    UsuarioModel create(UsuarioDTO usuarioDTO);

    UsuarioModel update(UUID id, UsuarioDTO usuarioDTO);

    UsuarioModel findById(UUID id);

    Page<UsuarioModel> findAll(Pageable pageable);
}