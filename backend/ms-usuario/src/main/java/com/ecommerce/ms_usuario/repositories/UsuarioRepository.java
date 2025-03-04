package com.ecommerce.ms_usuario.repositories;

import com.ecommerce.ms_usuario.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
