package com.valora.api.service;

import com.valora.api.dto.CategoriaDTO;
import com.valora.api.model.Categoria.CategoriaModel;
import com.valora.api.model.Usuario.UsuarioModel;
import com.valora.api.repository.CategoriaRepository;
import com.valora.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CategoriaModel salvar(CategoriaDTO dto) {
        UsuarioModel usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID informado!"));

        CategoriaModel categoria = new CategoriaModel();
        categoria.setUsuario(usuario);
        categoria.setNmCategoria(dto.nmCategoria());
        categoria.setDtCriacao(LocalDate.now());

        return categoriaRepository.save(categoria);
    }

    public List<CategoriaModel> listarTodas() {
        return categoriaRepository.findAll();
    }

    public List<CategoriaModel> listarPorUsuario(Long usuarioId) {
        return categoriaRepository.findByUsuarioId(usuarioId);
    }

    public CategoriaModel buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
    }

    public CategoriaModel atualizar(Long id, CategoriaDTO dto) {
        CategoriaModel categoriaExistente = buscarPorId(id);
        categoriaExistente.setNmCategoria(dto.nmCategoria());
        return categoriaRepository.save(categoriaExistente);
    }

    public void excluir(Long id) {
        CategoriaModel categoria = buscarPorId(id);
        categoriaRepository.delete(categoria);
    }
}