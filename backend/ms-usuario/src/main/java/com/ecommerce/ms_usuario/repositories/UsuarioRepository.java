package com.ecommerce.ms_usuario.repositories;

import com.ecommerce.ms_usuario.models.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {

    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}
