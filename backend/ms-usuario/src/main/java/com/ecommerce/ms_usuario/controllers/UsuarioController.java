package com.ecommerce.ms_usuario.controllers;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.services.UsuarioService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity<UsuarioDTO> create(@RequestBody
                                             @JsonView(UsuarioDTO.UsuarioView.Cadastro.class)
                                             UsuarioDTO usuarioDTO) {
        UsuarioDTO novoUsuarioDTO = usuarioService.create(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuarioDTO);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioDTO> update(@RequestBody
                                             @JsonView(UsuarioDTO.UsuarioView.Atualizar.class)
                                             UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioAtualizadoDTO = usuarioService.update(usuarioDTO);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizadoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
    }
}
