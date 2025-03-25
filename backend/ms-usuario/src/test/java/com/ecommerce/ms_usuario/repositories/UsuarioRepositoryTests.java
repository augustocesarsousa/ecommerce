package com.ecommerce.ms_usuario.repositories;

import com.ecommerce.ms_usuario.models.UsuarioModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private String existingLogin;
    private String notExistingLogin;
    private String existingEmail;
    private String notExistingEmail;

    @BeforeEach
    void setUp() throws Exception {
        existingLogin = "batman";
        notExistingLogin = "robin";
        existingEmail = "bruce.wayne@email.com";
        notExistingEmail = "dick.grayson@email.com";
    }

    @Test
    public void findByLoginShouldReturnModelWhenLoginExists() {
        UsuarioModel usuarioModel = usuarioRepository.findByLogin(existingLogin);

        Assertions.assertNotNull(usuarioModel);
    }

    @Test
    public void findByLoginShouldNullWhenLoginDoesNotExists() {
        UsuarioModel usuarioModel = usuarioRepository.findByLogin(notExistingLogin);

        Assertions.assertNull(usuarioModel);
    }

    @Test
    public void findByEmailShouldReturnModelWhenEmailExists() {
        UsuarioModel usuarioModel = usuarioRepository.findByEmail(existingEmail);

        Assertions.assertNotNull(usuarioModel);
    }

    @Test
    public void findByEmailShouldReturnNullWhenEmailDoesNotExists() {
        UsuarioModel usuarioModel = usuarioRepository.findByEmail(notExistingEmail);

        Assertions.assertNull(usuarioModel);
    }

}
