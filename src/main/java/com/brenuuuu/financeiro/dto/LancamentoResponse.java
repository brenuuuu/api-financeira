package com.brenuuuu.financeiro.dto;

import com.brenuuuu.financeiro.model.Lancamento;
import com.brenuuuu.financeiro.model.StatusLancamento;
import com.brenuuuu.financeiro.model.TipoLancamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoResponse(
        Long id,
        String descricao,
        String pessoa,
        BigDecimal valor,
        TipoLancamento tipo,
        StatusLancamento status,
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        String categoria
) {

    public static LancamentoResponse de(Lancamento l) {
        return new LancamentoResponse(
                l.getId(), l.getDescricao(), l.getPessoa(), l.getValor(), l.getTipo(),
                l.getStatus(), l.getDataVencimento(), l.getDataPagamento(),
                l.getCategoria().getNome());
    }
}
