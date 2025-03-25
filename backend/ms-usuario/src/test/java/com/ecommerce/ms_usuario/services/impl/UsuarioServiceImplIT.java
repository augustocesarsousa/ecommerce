package com.ecommerce.ms_usuario.services.impl;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import com.ecommerce.ms_usuario.services.UsuarioService;
import com.ecommerce.ms_usuario.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class UsuarioServiceImplIT {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioDTO usuarioValidoDTO;
    private UUID notExistingId;

    @BeforeEach
    void setUp() throws Exception {
        usuarioValidoDTO = UsuarioFactory.criarUsuarioValidoDTO();
        notExistingId = UUID.fromString("9ef38163-8438-43f2-847b-200e5e01b78f");
    }

    @Test
    public void createShouldPersistEntityInDatabaseWhenValidDatas() {
        UsuarioModel usuarioModel = usuarioService.create(usuarioValidoDTO);

        Assertions.assertNotNull(usuarioModel.getId());
        Assertions.assertTrue(usuarioRepository.existsById(usuarioModel.getId()));
    }

    @Test
    public void updateShouldUpdateEntityInDatabaseWhenValidDatas() {
        UsuarioModel usuarioCriado = usuarioService.create(usuarioValidoDTO);

        usuarioValidoDTO.setNome("John Stewart");
        UsuarioModel usuarioAtualizado = usuarioService.update(usuarioCriado.getId(), usuarioValidoDTO);

        Assertions.assertEquals("John Stewart", usuarioAtualizado.getNome());
        Assertions.assertEquals(usuarioCriado.getId(), usuarioAtualizado.getId());
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenNotExistingId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.update(notExistingId, usuarioValidoDTO);
        });
    }

    @Test
    public void findByIdShouldReturnEntityWhenExistingId() {
        UsuarioModel usuarioCriado = usuarioService.create(usuarioValidoDTO);

        UsuarioModel usuarioEncontrado = usuarioService.findById(usuarioCriado.getId());

        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertEquals(usuarioCriado.getId(), usuarioEncontrado.getId());
    }


}
