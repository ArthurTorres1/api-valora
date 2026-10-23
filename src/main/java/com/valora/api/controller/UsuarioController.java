package com.valora.api.controller;

import com.valora.api.dto.AtualizaUsuarioDTO;
import com.valora.api.dto.CadastroUsuarioDTO;
import com.valora.api.dto.DadosListagemUsuarioDTO;
import com.valora.api.model.Usuario.UsuarioModel;
import com.valora.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/listar-usuarios")
    public Page<DadosListagemUsuarioDTO> listarUsuarios(@PageableDefault(size = 12, page = 0, sort = {"nome"}) Pageable page) {
        return usuarioRepository
                .findAllByAtivoTrue(page)
                .map(DadosListagemUsuarioDTO::new);
    }

    @PutMapping("/atualizar")
    @Transactional
    public void atualizarUsuario(@RequestBody @Valid AtualizaUsuarioDTO novoUsuarioDTO) {
        var usuario = usuarioRepository.getReferenceById(novoUsuarioDTO.id());
        usuario.atualizarInformacoes(novoUsuarioDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.desativar();
    }
}
