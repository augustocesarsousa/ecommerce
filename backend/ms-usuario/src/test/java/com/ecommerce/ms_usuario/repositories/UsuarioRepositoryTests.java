package com.ecommerce.ms_usuario.repositories;

import com.ecommerce.ms_usuario.models.UsuarioModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String existsLogin;
    private String notExistsLogin;
    private String existsEmail;
    private String notExistisEmail;

    @BeforeEach
    void setUp() throws Exception {
        existsLogin = "batman";
        notExistsLogin = "robin";
        existsEmail = "bruce.wayne@email.com";
        notExistisEmail = "dick.grayson@email.com";
    }

    @Test
    public void findByLoginShouldReturnModelWhenLoginExists() {
        UsuarioModel usuarioModel = usuarioRepository.findByLogin(existsLogin);

        Assertions.assertNotNull(usuarioModel);
    }

    @Test
    public void findByLoginShouldNullWhenLoginDoesNotExists() {
        UsuarioModel usuarioModel = usuarioRepository.findByLogin(notExistsLogin);

        Assertions.assertNull(usuarioModel);
    }

    @Test
    public void findByEmailShouldReturnModelWhenEmailExists() {
        UsuarioModel usuarioModel = usuarioRepository.findByEmail(existsEmail);

        Assertions.assertNotNull(usuarioModel);
    }

    @Test
    public void findByEmailShouldReturnNullWhenEmailDoesNotExists() {
        UsuarioModel usuarioModel = usuarioRepository.findByEmail(notExistisEmail);

        Assertions.assertNull(usuarioModel);
    }

}
