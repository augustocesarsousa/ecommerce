package com.ecommerce.ms_empresa.repositories;

import com.ecommerce.ms_empresa.models.EmpresaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmpresaRepository extends JpaRepository<EmpresaModel, UUID> {
    Optional<EmpresaModel> findFirstByOrderByIdAsc();
}
