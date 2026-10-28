package com.valora.api.repository;

import com.valora.api.domain.Conta.ContaModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<ContaModel, Long> {
    Page<ContaModel> findAllByAtivoTrue(Pageable page);
}
