package com.valora.api.repository;

import com.valora.api.model.Usuario.UsuarioModel;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
}
