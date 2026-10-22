package com.valora.api.repository;

import com.valora.api.model.Categoria.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {
    List<CategoriaModel> findByUsuarioId(Long usuarioId);
}