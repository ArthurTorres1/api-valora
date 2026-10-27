package com.valora.api.controller;

import com.valora.api.domain.Caixinha.CaixinhaDTO;
import com.valora.api.domain.Caixinha.CaixinhaModel;
import com.valora.api.service.CaixinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caixinhas")
public class CaixinhaController {

    @Autowired
    private CaixinhaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CaixinhaModel salvar(@RequestBody CaixinhaDTO dto) {
        return service.salvar(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CaixinhaModel> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/usuario/{usuarioId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CaixinhaModel> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CaixinhaModel buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CaixinhaModel atualizar(@PathVariable Long id, @RequestBody CaixinhaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}