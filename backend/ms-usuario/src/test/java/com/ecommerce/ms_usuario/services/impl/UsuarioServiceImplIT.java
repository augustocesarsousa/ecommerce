package com.ecommerce.ms_usuario.services.impl;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import com.ecommerce.ms_usuario.services.UsuarioService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class UsuarioServiceImplIT {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() throws Exception {
        usuarioDTO = UsuarioFactory.criarUsuarioValidoDTO();
    }

    @Test
    public void createShouldPersistEntityInDatabaseWhenValidDatas() {
        UsuarioModel usuarioModel = usuarioService.create(usuarioDTO);

        Assertions.assertNotNull(usuarioModel.getId());
        Assertions.assertTrue(usuarioRepository.existsById(usuarioModel.getId()));
    }
}
