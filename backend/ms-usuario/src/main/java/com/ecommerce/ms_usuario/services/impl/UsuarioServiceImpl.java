package com.ecommerce.ms_usuario.services.impl;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.models.Usuario;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import com.ecommerce.ms_usuario.services.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        var usuario = new Usuario();

        BeanUtils.copyProperties(usuarioDTO, usuario);
        usuario.setDtCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        usuario = usuarioRepository.save(usuario);
        BeanUtils.copyProperties(usuario, usuarioDTO);

        return usuarioDTO;
    }
}
