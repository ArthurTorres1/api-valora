package com.valora.api.service;

import com.valora.api.dto.CaixinhaDTO;
import com.valora.api.model.Caixinha.CaixinhaModel;
import com.valora.api.model.Usuario.UsuarioModel;
import com.valora.api.repository.CaixinhaRepository;
import com.valora.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CaixinhaService {

    @Autowired
    private CaixinhaRepository caixinhaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CaixinhaModel salvar(CaixinhaDTO dto) {
        UsuarioModel usuario =
usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID informado!"));

        CaixinhaModel caixinha = new CaixinhaModel();
        caixinha.setUsuario(usuario);
        caixinha.setNmCaixinha(dto.nmCaixinha());
        caixinha.setVlMeta(dto.vlMeta());
        caixinha.setVlAtual(dto.vlAtual());
        caixinha.setDtCriacao(LocalDate.now());

        return caixinhaRepository.save(caixinha);
    }

    public List<CaixinhaModel> listarTodas() {
        return caixinhaRepository.findAll();
    }

    public List<CaixinhaModel> listarPorUsuario(Long usuarioId) {
        return caixinhaRepository.findByUsuarioId(usuarioId);
    }

    public CaixinhaModel buscarPorId(Long id) {
        return caixinhaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Caixinha não encontrada!"));
    }

    public CaixinhaModel atualizar(Long id, CaixinhaDTO dto) {
        CaixinhaModel caixinhaExistente = buscarPorId(id);
        caixinhaExistente.setNmCaixinha(dto.nmCaixinha());
        caixinhaExistente.setVlMeta(dto.vlMeta());
        caixinhaExistente.setVlAtual(dto.vlAtual());
        return caixinhaRepository.save(caixinhaExistente);
    }

    public void excluir(Long id) {
        CaixinhaModel caixinha = buscarPorId(id);
        caixinhaRepository.delete(caixinha);
    }
}