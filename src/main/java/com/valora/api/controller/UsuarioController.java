package com.valora.api.controller;

import com.valora.api.dto.AtualizaUsuarioDTO;
import com.valora.api.dto.CadastroUsuarioDTO;
import com.valora.api.dto.DadosListagemUsuarioDTO;
import com.valora.api.dto.UsuarioDetalhadoDTO;
import com.valora.api.model.Usuario.UsuarioModel;
import com.valora.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastro(@RequestBody @Valid CadastroUsuarioDTO cadastroUsuarioDTO, UriComponentsBuilder uriBuilder) {
        var newUsuario = usuarioRepository.save(new UsuarioModel(cadastroUsuarioDTO));
        var uri = uriBuilder.path("/api/usuario/{id}").buildAndExpand(newUsuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new UsuarioDetalhadoDTO(newUsuario));
    }

    @GetMapping("/listar-usuarios")
    public ResponseEntity<Page<DadosListagemUsuarioDTO>> listarUsuarios(@PageableDefault(size = 12, page = 0, sort = {"nome"}) Pageable page) {
        var listagemUsuarios = usuarioRepository
                .findAllByAtivoTrue(page)
                .map(DadosListagemUsuarioDTO::new);

        return ResponseEntity.ok(listagemUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDetalhadoDTO> detalharUsuario(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok(new UsuarioDetalhadoDTO(usuario));
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity atualizarUsuario(@RequestBody @Valid AtualizaUsuarioDTO novoUsuarioDTO) {
        var usuario = usuarioRepository.getReferenceById(novoUsuarioDTO.id());
        usuario.atualizarInformacoes(novoUsuarioDTO);

        return ResponseEntity.ok(new UsuarioDetalhadoDTO(usuario));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        usuario.desativar();
        return ResponseEntity.noContent().build();
    }
}
