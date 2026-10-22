package com.valora.api.repository;

import com.valora.api.model.Usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

}
