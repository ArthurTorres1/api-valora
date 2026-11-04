package com.valora.api.controller;

import com.valora.api.domain.Movimentacao.*;
import com.valora.api.domain.Movimentacao.dto.AtualizaMovimentacaoDTO;
import com.valora.api.domain.Movimentacao.dto.CadastroMovimentacaoDTO;
import com.valora.api.domain.Movimentacao.dto.DadosListagemMovimentacaoDTO;
import com.valora.api.domain.Movimentacao.dto.MovimentacaoDetalhadaDTO;
import com.valora.api.domain.Usuario.UsuarioModel;
import com.valora.api.repository.*;
import com.valora.api.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;


    @PostMapping
    @Transactional
    public ResponseEntity<MovimentacaoDetalhadaDTO> cadastrar(
            @RequestBody @Valid CadastroMovimentacaoDTO dto,
            @AuthenticationPrincipal UsuarioModel usuarioLogado,
            UriComponentsBuilder uriBuilder) {

        var novaMovimentacao = movimentacaoService.salvarMovimentacao(dto, usuarioLogado);

        var uri = uriBuilder.path("/api/movimentacao/{id}").buildAndExpand(novaMovimentacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new MovimentacaoDetalhadaDTO(novaMovimentacao));
    }

    @GetMapping("/listar-movimentacoes")
    public ResponseEntity<Page<DadosListagemMovimentacaoDTO>> listarMovimentacoes(
            @RequestParam(required = false) Long contaId,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) TipoMovimentacao tpMovimentacao,
            @PageableDefault(size = 12, page = 0, sort = {"dataMovimentacao"}) Pageable page,
            @AuthenticationPrincipal UsuarioModel usuarioLogado) {

        var listagem = movimentacaoService.getMovimentacoes(usuarioLogado, contaId, categoriaId, tpMovimentacao, page);
        return ResponseEntity.ok(listagem);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoDetalhadaDTO> detalharMovimentacao(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioModel usuarioLogado) {

        var movimentacao = movimentacaoService.detalharMovimentacao(id, usuarioLogado);
        return ResponseEntity.ok(new MovimentacaoDetalhadaDTO(movimentacao));
    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity atualizarMovimentacao(
            @PathVariable Long id,
            @RequestBody @Valid AtualizaMovimentacaoDTO dto,
            @AuthenticationPrincipal UsuarioModel usuarioLogado) {

        var movimentacao = movimentacaoService.atualizarMovimentacao(id, dto, usuarioLogado);

        return ResponseEntity.ok(new MovimentacaoDetalhadaDTO(movimentacao));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioModel usuarioLogado) {

        movimentacaoService.desativarMovimentacao(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }
}