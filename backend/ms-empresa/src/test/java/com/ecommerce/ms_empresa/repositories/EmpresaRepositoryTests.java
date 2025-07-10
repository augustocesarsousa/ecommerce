package com.ecommerce.ms_empresa.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class EmpresaRepositoryTests {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Test
    public void findFirstByOrderByIdAscShouldReturnNothingWhenEntityDoesNotExisting() {
        Assertions.assertFalse(empresaRepository.findFirstByOrderByIdAsc().isPresent());
    }
}
