package com.ecommerce.ms_usuario.services.impl;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import com.ecommerce.ms_usuario.models.Usuario;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import com.ecommerce.ms_usuario.services.UsuarioService;
import com.ecommerce.ms_usuario.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        var usuario = new Usuario();

        BeanUtils.copyProperties(usuarioDTO, usuario);
        usuario.setStatus(UsuarioStatus.ATIVO);
        usuario.setDtCriacao(LocalDateTime.now(ZoneId.of("UTC")));
        usuario = usuarioRepository.save(usuario);
        BeanUtils.copyProperties(usuario, usuarioDTO);

        return usuarioDTO;
    }

    @Override
    public UsuarioDTO update(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioDTO.getId());
        Usuario usuario = usuarioOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setNome(usuarioDTO.getNome());
        if(usuarioDTO.getSenha() != null) {
            usuario.setSenha(usuarioDTO.getSenha());
        }
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setStatus(usuarioDTO.getStatus());
        usuario.setPerfil(usuarioDTO.getPerfil());
        usuario.setDtUltAlteracao(LocalDateTime.now(ZoneId.of("UTC")));
        usuario = usuarioRepository.save(usuario);

        BeanUtils.copyProperties(usuario, usuarioDTO);

        return usuarioDTO;
    }

    @Override
    public UsuarioDTO findById(UUID id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        Usuario usuario = usuarioOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        BeanUtils.copyProperties(usuario, usuarioDTO);
        return usuarioDTO;
    }
}
