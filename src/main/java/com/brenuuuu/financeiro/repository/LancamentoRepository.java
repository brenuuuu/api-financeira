package com.brenuuuu.financeiro.repository;

import com.brenuuuu.financeiro.model.Lancamento;
import com.brenuuuu.financeiro.model.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

    List<Lancamento> findByTipoOrderByDataVencimento(TipoLancamento tipo);

    List<Lancamento> findByDataVencimentoBetweenOrderByDataVencimento(LocalDate inicio, LocalDate fim);

    boolean existsByCategoriaId(Long categoriaId);
}
