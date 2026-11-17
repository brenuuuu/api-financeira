package com.brenuuuu.financeiro.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LancamentoTest {

    private Lancamento contaQueVenceEm(LocalDate vencimento) {
        Categoria aluguel = new Categoria("Aluguel", TipoLancamento.DESPESA);
        return new Lancamento("Aluguel", "Imobiliária", new BigDecimal("1000.00"),
                TipoLancamento.DESPESA, vencimento, aluguel);
    }

    @Test
    void contaQueAindaNaoVenceuFicaPendente() {
        Lancamento conta = contaQueVenceEm(LocalDate.now().plusDays(5));
        assertEquals(StatusLancamento.PENDENTE, conta.getStatus());
    }

    @Test
    void contaVencidaENaoPagaFicaAtrasada() {
        Lancamento conta = contaQueVenceEm(LocalDate.now().minusDays(1));
        assertEquals(StatusLancamento.ATRASADO, conta.getStatus());
    }

    @Test
    void contaComDataDePagamentoFicaPaga() {
        Lancamento conta = contaQueVenceEm(LocalDate.now().minusDays(10));
        conta.setDataPagamento(LocalDate.now());
        assertEquals(StatusLancamento.PAGO, conta.getStatus());
    }
}
