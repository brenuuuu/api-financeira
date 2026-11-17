package com.brenuuuu.financeiro.service;

import com.brenuuuu.financeiro.dto.ResumoResponse;
import com.brenuuuu.financeiro.exception.RegraDeNegocioException;
import com.brenuuuu.financeiro.model.Lancamento;
import com.brenuuuu.financeiro.model.StatusLancamento;
import com.brenuuuu.financeiro.model.TipoLancamento;
import com.brenuuuu.financeiro.repository.LancamentoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Resumo financeiro de um periodo (pela data de vencimento).
 */
@Service
public class ResumoService {

    private final LancamentoRepository repository;

    public ResumoService(LancamentoRepository repository) {
        this.repository = repository;
    }

    public ResumoResponse gerar(LocalDate inicio, LocalDate fim) {
        if (fim.isBefore(inicio)) {
            throw new RegraDeNegocioException("A data final deve ser depois da data inicial");
        }

        List<Lancamento> lancamentos = repository.findByDataVencimentoBetweenOrderByDataVencimento(inicio, fim);

        BigDecimal receitas = BigDecimal.ZERO;
        BigDecimal despesas = BigDecimal.ZERO;
        BigDecimal emAtraso = BigDecimal.ZERO;

        for (Lancamento l : lancamentos) {
            if (l.getTipo() == TipoLancamento.RECEITA) {
                receitas = receitas.add(l.getValor());
            } else {
                despesas = despesas.add(l.getValor());
            }
            if (l.getStatus() == StatusLancamento.ATRASADO) {
                emAtraso = emAtraso.add(l.getValor());
            }
        }

        return new ResumoResponse(inicio, fim, receitas, despesas, receitas.subtract(despesas), emAtraso);
    }
}
