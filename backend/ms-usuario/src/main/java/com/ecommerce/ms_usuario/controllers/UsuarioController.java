package com.ecommerce.ms_usuario.controllers;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.services.UsuarioService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity<UsuarioModel> create(@RequestBody
                                             @JsonView(UsuarioDTO.UsuarioView.Cadastrar.class)
                                             @Valid UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(usuarioDTO));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioModel> update(@RequestBody
                                             @JsonView(UsuarioDTO.UsuarioView.Atualizar.class)
                                             @Valid UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.update(usuarioDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioModel>> findAll(@PageableDefault(
                                                            page = 0,
                                                            size = 10,
                                                            sort = "id",
                                                            direction = Sort.Direction.ASC)
                                                          Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll(pageable));
    }
}
