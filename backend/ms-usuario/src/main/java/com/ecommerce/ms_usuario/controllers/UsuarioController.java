package com.ecommerce.ms_usuario.controllers;

import com.ecommerce.ms_usuario.dtos.UsuarioDTO;
import com.ecommerce.ms_usuario.enums.UsuarioPerfil;
import com.ecommerce.ms_usuario.enums.UsuarioStatus;
import com.ecommerce.ms_usuario.models.UsuarioModel;
import com.ecommerce.ms_usuario.records.EnumRecord;
import com.ecommerce.ms_usuario.services.UsuarioService;
import com.ecommerce.ms_usuario.spacifications.queryFilters.UsuarioQueryFilter;
import com.ecommerce.ms_usuario.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuarios")
public class  UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioModel> create(@RequestBody
                                             @JsonView(UsuarioDTO.UsuarioView.Cadastrar.class)
                                             @Validated(UsuarioDTO.UsuarioView.Cadastrar.class) UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.create(usuarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> update(@PathVariable(value = "id") UUID id, @RequestBody
                                             @JsonView(UsuarioDTO.UsuarioView.Atualizar.class)
                                             @Validated(UsuarioDTO.UsuarioView.Atualizar.class) UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.update(id, usuarioDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> findById(@PathVariable(value = "id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioModel>> findAll(UsuarioQueryFilter filter,
                                                        @PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.ASC)
                                                        Pageable pageable) {
        Page<UsuarioModel> usuarioModelPage = usuarioService.findAll(filter.toSpecification(), pageable);
        if(!usuarioModelPage.isEmpty()){
            for(UsuarioModel usuarioModel : usuarioModelPage.toList()) {
                usuarioModel.add(linkTo(methodOn(UsuarioController.class).findById(usuarioModel.getId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(usuarioModelPage);
    }

    @GetMapping("/status")
    public ResponseEntity<List<EnumRecord>> getStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(EnumUtil.convertEnumToList(UsuarioStatus.class));
    }

    @GetMapping("/perfis")
    public ResponseEntity<List<EnumRecord>> getPerfis() {
        return ResponseEntity.status(HttpStatus.OK).body(EnumUtil.convertEnumToList(UsuarioPerfil.class));
    }
}
