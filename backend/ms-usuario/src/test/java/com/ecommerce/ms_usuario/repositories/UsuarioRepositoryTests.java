package com.ecommerce.ms_usuario.repositories;

import com.ecommerce.ms_usuario.models.UsuarioModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void findByLoginShouldReturnModelWhenLoginExists() {
        String existsLogin = "batman";

        UsuarioModel usuarioModel = usuarioRepository.findByLogin(existsLogin);

        Assertions.assertNotNull(usuarioModel);
    }

    @Test
    void findByLoginShouldNullWhenLoginDoesNotExists() {
        String notExistsLogin = "robin";

        UsuarioModel usuarioModel = usuarioRepository.findByLogin(notExistsLogin);

        Assertions.assertNull(usuarioModel);
    }

}
