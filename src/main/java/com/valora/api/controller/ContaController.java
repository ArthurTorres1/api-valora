package com.valora.api.controller;

import com.valora.api.domain.Conta.AtualizaContaDTO;
import com.valora.api.domain.Conta.CadastroContaDTO;
import com.valora.api.domain.Conta.DadosListagemContaDTO;
import com.valora.api.domain.Conta.ContaDetalhadaDTO;
import com.valora.api.domain.Conta.ContaModel;
import com.valora.api.repository.ContaRepository;
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
@RequestMapping("/api/conta")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastro(@RequestBody @Valid CadastroContaDTO cadastroContaDTO, UriComponentsBuilder uriBuilder) {

        var usuario = usuarioRepository.getReferenceById(cadastroContaDTO.idUsuario());
        var novaConta = contaRepository.save(new ContaModel(cadastroContaDTO, usuario));
        var uri = uriBuilder.path("/api/conta/{id}").buildAndExpand(novaConta.getId()).toUri();

        return ResponseEntity.created(uri).body(new ContaDetalhadaDTO(novaConta));
    }

    @GetMapping("/listar-contas")
    public ResponseEntity<Page<DadosListagemContaDTO>> listarContas(@PageableDefault(size = 12, page = 0, sort = {"nome"}) Pageable page) {
        var listagemContas = contaRepository
                .findAllByAtivoTrue(page)
                .map(DadosListagemContaDTO::new);

        return ResponseEntity.ok(listagemContas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDetalhadaDTO> detalharConta(@PathVariable Long id) {
        var conta = contaRepository.getReferenceById(id);
        return ResponseEntity.ok(new ContaDetalhadaDTO(conta));
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity atualizarConta(@RequestBody @Valid AtualizaContaDTO novaContaDTO) {
        var conta = contaRepository.getReferenceById(novaContaDTO.id());
        conta.atualizarInformacoes(novaContaDTO);

        return ResponseEntity.ok(new ContaDetalhadaDTO(conta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var conta = contaRepository.getReferenceById(id);
        conta.desativar();
        return ResponseEntity.noContent().build();
    }
}