package com.brenuuuu.financeiro.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Um lançamento financeiro: uma conta a pagar (DESPESA) ou a receber (RECEITA).
 */
@Entity
@Table(name = "lancamento")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String descricao;

    /** Cliente (receita) ou fornecedor (despesa). */
    @Column(length = 120)
    private String pessoa;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoLancamento tipo;

    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Lancamento() {
    }

    public Lancamento(String descricao, String pessoa, BigDecimal valor, TipoLancamento tipo,
                      LocalDate dataVencimento, Categoria categoria) {
        this.descricao = descricao;
        this.pessoa = pessoa;
        this.valor = valor;
        this.tipo = tipo;
        this.dataVencimento = dataVencimento;
        this.categoria = categoria;
    }

    /**
     * O status não fica salvo no banco: ele é calculado.
     * Se tem data de pagamento, está PAGO; se o vencimento já passou, está ATRASADO.
     */
    public StatusLancamento getStatus() {
        if (dataPagamento != null) {
            return StatusLancamento.PAGO;
        }
        if (dataVencimento.isBefore(LocalDate.now())) {
            return StatusLancamento.ATRASADO;
        }
        return StatusLancamento.PENDENTE;
    }

    public Long getId() { return id; }
    public String getDescricao() { return descricao; }
    public String getPessoa() { return pessoa; }
    public BigDecimal getValor() { return valor; }
    public TipoLancamento getTipo() { return tipo; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public Categoria getCategoria() { return categoria; }

    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setPessoa(String pessoa) { this.pessoa = pessoa; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public void setTipo(TipoLancamento tipo) { this.tipo = tipo; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
