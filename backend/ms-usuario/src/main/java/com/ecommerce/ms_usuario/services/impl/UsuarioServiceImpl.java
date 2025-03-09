package com.ecommerce.ms_usuario.services.impl;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import com.ecommerce.ms_usuario.services.UsuarioService;
import com.ecommerce.ms_usuario.services.exceptions.ExistingAttributeException;
import com.ecommerce.ms_usuario.services.exceptions.ResourceNotFoundException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UsuarioModel create(UsuarioDTO usuarioDTO) {
        if(usuarioRepository.existsByLogin(usuarioDTO.getLogin())) {
            throw new ExistingAttributeException("Login já cadastrado");
        }
        if(usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new ExistingAttributeException("E-mail já cadastrado");
        }
        UsuarioModel usuarioModel = new UsuarioModel();

        BeanUtils.copyProperties(usuarioDTO, usuarioModel);
        usuarioModel.setStatus(UsuarioStatus.ATIVO);
        usuarioModel.setDtCriacao(Instant.now());
        usuarioModel = usuarioRepository.save(usuarioModel);

        return usuarioModel;
    }

    @Override
    public UsuarioModel update(UsuarioDTO usuarioDTO) {
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(usuarioDTO.getId());
        UsuarioModel usuarioModel = usuarioModelOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        if(usuarioRepository.existsByLogin(usuarioDTO.getLogin())) {
            throw new ExistingAttributeException("Login já cadastrado");
        }
        if(usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new ExistingAttributeException("E-mail já cadastrado");
        }

        BeanUtils.copyProperties(usuarioDTO, usuarioModel);

        if(usuarioModel.getSenha() != null) {
            usuarioModel.setSenha(usuarioModelOptional.get().getSenha());
        }
        usuarioModel.setLogin(usuarioModelOptional.get().getLogin());
        usuarioModel.setDtCriacao(usuarioModelOptional.get().getDtCriacao());
        usuarioModel.setDtUltAlteracao(Instant.now());
        usuarioModel = usuarioRepository.save(usuarioModel);

        return usuarioModel;
    }

    @Override
    public UsuarioModel findById(UUID id) {
        Optional<UsuarioModel> usuarioModelOptional = usuarioRepository.findById(id);
        UsuarioModel usuarioModel = usuarioModelOptional.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return usuarioModel;
    }

    @Override
    public List<UsuarioModel> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public boolean existsByLogin(String login) {
        return usuarioRepository.existsByLogin(login);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
