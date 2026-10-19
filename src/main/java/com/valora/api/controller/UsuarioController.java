package com.valora.api.controller;

import com.valora.api.DTOs.CadastroUsuarioDTO;
import com.valora.api.models.Usuario.UsuarioModel;
import com.valora.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public void cadastro(@RequestBody @Valid CadastroUsuarioDTO cadastroUsuarioDTO) {

        usuarioRepository.save(new UsuarioModel(cadastroUsuarioDTO));
    }
}
