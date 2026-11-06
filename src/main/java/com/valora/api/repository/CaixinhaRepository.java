package com.valora.api.repository;

import com.valora.api.domain.Caixinha.CaixinhaModel;
import com.valora.api.domain.Usuario.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaixinhaRepository extends JpaRepository<CaixinhaModel, Long> {
    List<CaixinhaModel> findByUsuarioId(Long usuarioId);

    Optional<CaixinhaModel> findByIdAndUsuario(Long aLong, UsuarioModel usuarioLogado);

    List<CaixinhaModel> findAllByUsuarioAndAtivoTrue(UsuarioModel usuario);
}