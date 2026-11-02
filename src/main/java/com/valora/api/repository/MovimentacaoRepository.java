package com.valora.api.repository;

import com.valora.api.domain.Movimentacao.MovimentacaoModel;
import com.valora.api.domain.Movimentacao.TipoMovimentacao;
import com.valora.api.domain.Usuario.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoModel, Long> {

    @Query("""
        SELECT m FROM MovimentacaoModel m
        WHERE m.usuario = :usuario
        AND m.ativo = true
        AND (:contaId IS NULL OR m.contaOrigem.id = :contaId)
        AND (:categoriaId IS NULL OR m.categoria.id = :categoriaId)
        AND (:tpMovimentacao IS NULL OR m.tipo = :tpMovimentacao)
    """)
    Page<MovimentacaoModel> buscarComFiltrosOpcionais(
            @Param("usuario") UsuarioModel usuario,
            @Param("contaId") Long contaId,
            @Param("categoriaId") Long categoriaId,
            @Param("tpMovimentacao") TipoMovimentacao tpMovimentacao,
            Pageable pageable
    );

    Optional<MovimentacaoModel> findByIdAndUsuario(Long id, UsuarioModel usuario);

}
