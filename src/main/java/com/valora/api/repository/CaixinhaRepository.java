package com.valora.api.repository;

import com.valora.api.model.Caixinha.CaixinhaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaixinhaRepository extends JpaRepository<CaixinhaModel, Long> {
    List<CaixinhaModel> findByUsuarioId(Long usuarioId);
}