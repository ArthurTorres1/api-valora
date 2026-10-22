package com.valora.api.controller;

import com.valora.api.dto.CategoriaDTO;
import com.valora.api.model.Categoria.CategoriaModel;
import com.valora.api.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaModel salvar(@RequestBody CategoriaDTO dto) {
        return service.salvar(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoriaModel> listarTodas() {
        return service.listarTodas();
    }

    @GetMapping("/usuario/{usuarioId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoriaModel> listarPorUsuario(@PathVariable Long usuarioId) {
        return service.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoriaModel buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoriaModel atualizar(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}