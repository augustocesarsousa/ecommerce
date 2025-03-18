package com.ecommerce.ms_usuario.repositories;

import com.ecommerce.ms_usuario.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID>, JpaSpecificationExecutor<UsuarioModel> {

    UsuarioModel findByLogin(String login);

    UsuarioModel findByEmail(String email);
}
