package com.ecommerce.ms_empresa.controllers;

import com.ecommerce.ms_empresa.dtos.EmpresaDTO;
import com.ecommerce.ms_empresa.models.EmpresaModel;
import com.ecommerce.ms_empresa.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    EmpresaService empresaService;

    @PostMapping
    public ResponseEntity<EmpresaModel> create(@RequestBody EmpresaDTO empresaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.create(empresaDTO));
    }

    @GetMapping
    public ResponseEntity<EmpresaModel> findOne() {
        return ResponseEntity.status(HttpStatus.OK).body((empresaService.findOne()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaModel> updade(@PathVariable(value = "id") UUID id, EmpresaDTO empresaDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(empresaService.update(id, empresaDTO));
    }
}
