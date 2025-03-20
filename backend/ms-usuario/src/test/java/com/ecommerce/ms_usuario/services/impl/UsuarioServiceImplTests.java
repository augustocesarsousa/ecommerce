package com.ecommerce.ms_usuario.services.impl;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import com.ecommerce.ms_usuario.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UsuarioServiceImplTests {

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    private UsuarioDTO usuarioValidoDTO;
    private UsuarioModel usuarioModel;

    private UUID existingId;
    private UUID notExistingId;

    @BeforeEach
    void setUp() throws Exception {
        usuarioModel = UsuarioFactory.criarUsuarioModel();
        usuarioValidoDTO = UsuarioFactory.criarUsuarioValidoDTO();
        existingId = UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f");
        notExistingId = UUID.fromString("3ef38163-8438-43f2-847b-200e5e01b78f");

        when(usuarioRepository.save(any())).thenReturn(usuarioModel);
        when(usuarioRepository.findById(existingId)).thenReturn(Optional.of(usuarioModel));
    }

    @Test
    public void createShouldPersistEntityWhenValidDatas() {
        UsuarioModel usuarioModel = usuarioService.create(usuarioValidoDTO);

        Assertions.assertNotNull(usuarioModel.getId());
    }

    @Test
    public void updateShouldPersistEntityWhenValidDatas() {
        UsuarioModel usuarioModel = usuarioService.update(existingId, usuarioValidoDTO);

        Assertions.assertNotNull(usuarioModel);
        Assertions.assertEquals(usuarioModel.getId(), existingId);
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenNotExistingId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.update(notExistingId, usuarioValidoDTO);
        });
    }

    @Test
    public void findByIdShouldReturnEntityWhenExistingId() {
        UsuarioModel usuarioModel = usuarioService.findById(existingId);

        Assertions.assertNotNull(usuarioModel);
        Assertions.assertEquals(usuarioModel.getId(), existingId);
    }
}
