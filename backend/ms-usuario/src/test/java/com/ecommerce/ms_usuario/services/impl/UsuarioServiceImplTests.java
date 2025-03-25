package com.ecommerce.ms_usuario.services.impl;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.factories.UsuarioFactory;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.repositories.UsuarioRepository;
import com.ecommerce.ms_usuario.services.exceptions.ResourceNotFoundException;
import com.ecommerce.ms_usuario.spacifications.queryFilters.UsuarioQueryFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class UsuarioServiceImplTests {

    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @Mock
    UsuarioRepository usuarioRepository;

    private UsuarioDTO usuarioValidoDTO;
    private UUID existingId;
    private UUID notExistingId;
    private UsuarioQueryFilter usuarioQueryFilter;

    @BeforeEach
    void setUp() throws Exception {
        UsuarioModel usuarioModel = UsuarioFactory.criarUsuarioModel();
        usuarioValidoDTO = UsuarioFactory.criarUsuarioValidoDTO();
        existingId = UUID.fromString("2ef38163-8438-43f2-847b-200e5e01b78f");
        notExistingId = UUID.fromString("3ef38163-8438-43f2-847b-200e5e01b78f");
        usuarioQueryFilter = new UsuarioQueryFilter();
        PageImpl<UsuarioModel> usuarioModelPage = new PageImpl<>(List.of(usuarioModel));

        when(usuarioRepository.save(any())).thenReturn(usuarioModel);
        when(usuarioRepository.findById(existingId)).thenReturn(Optional.of(usuarioModel));
        when(usuarioRepository.findAll((Specification<UsuarioModel>) any(), (Pageable) any())).thenReturn(usuarioModelPage);
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

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenNotExistingId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.findById(notExistingId);
        });
    }

    @Test
    public void findAllShouldReturnPage() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<UsuarioModel> page = usuarioService.findAll(usuarioQueryFilter.toSpecification(), pageable);

        Assertions.assertNotNull(page);
    }
}
