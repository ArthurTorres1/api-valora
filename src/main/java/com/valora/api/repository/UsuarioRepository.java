package com.valora.api.repository;

import com.valora.api.domain.Usuario.UsuarioModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    Page<UsuarioModel> findAllByAtivoTrue(Pageable page);
}
