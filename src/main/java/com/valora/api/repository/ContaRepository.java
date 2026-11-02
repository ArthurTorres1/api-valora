package com.valora.api.repository;

import com.valora.api.domain.Conta.ContaModel;
import com.valora.api.domain.Usuario.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<ContaModel, Long> {
    Page<ContaModel> findAllByAtivoTrueAndUsuario(Pageable page, UsuarioModel usuario);

    Optional<ContaModel> findByIdAndUsuario(Long aLong, UsuarioModel usuarioLogado);
}
