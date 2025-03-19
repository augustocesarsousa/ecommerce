package com.ecommerce.ms_usuario.services.impl;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UsuarioServiceImplTests {

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    private UsuarioDTO usuarioValidoDTO;
    private UsuarioModel usuarioModel;

    @BeforeEach
    void setUp() throws Exception {
        usuarioModel = UsuarioFactory.criarUsuarioModel();
        usuarioValidoDTO = UsuarioFactory.criarUsuarioValidoDTO();

        when(usuarioRepository.save(any())).thenReturn(usuarioModel);
    }

    @Test
    public void createShouldPersistEntityWhenValidDatas() {
        UsuarioModel usuarioModel = usuarioService.create(usuarioValidoDTO);

        Assertions.assertNotNull(usuarioModel.getId());
    }
}
