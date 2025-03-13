package com.ecommerce.ms_usuario.services.impl;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import com.ecommerce.ms_usuario.services.UsuarioService;
import com.ecommerce.ms_usuario.services.exceptions.ExistingAttributeException;
import com.ecommerce.ms_usuario.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public UsuarioModel create(UsuarioDTO usuarioDTO) {
//        if(usuarioRepository.existsByLogin(usuarioDTO.getLogin())) {
//            throw new ExistingAttributeException("Login já cadastrado");
//        }
//        if(usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
//            throw new ExistingAttributeException("E-mail já cadastrado");
//        }
        UsuarioModel usuarioModel = new UsuarioModel();

        usuarioDTO.setStatus(UsuarioStatus.ATIVO);
        usuarioDTO.setDtCriacao(LocalDateTime.now(ZoneId.of("UTC")));

        BeanUtils.copyProperties(usuarioDTO, usuarioModel);
        return usuarioRepository.save(usuarioModel);
    }

    @Override
    public UsuarioModel update(UUID id, UsuarioDTO usuarioDTO) {
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(id);
        UsuarioModel usuarioModel = usuarioModelOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário para atualizar não encontrado"));

        if(usuarioDTO.getSenha() == null) {
            usuarioDTO.setSenha(usuarioModel.getSenha());
        }
        usuarioDTO.setId(usuarioModel.getId());
        usuarioDTO.setLogin(usuarioModel.getLogin());
        usuarioDTO.setDtCriacao(usuarioModel.getDtCriacao());
        usuarioDTO.setDtUltAlteracao(LocalDateTime.now(ZoneId.of("UTC")));

        BeanUtils.copyProperties(usuarioDTO, usuarioModel);
        return usuarioRepository.save(usuarioModel);
    }

    @Override
    public UsuarioModel findById(UUID id) {
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(id);
        return usuarioModelOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    @Override
    public Page<UsuarioModel> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }
}
