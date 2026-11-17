package com.brenuuuu.financeiro.dto;

import com.brenuuuu.financeiro.model.TipoLancamento;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LancamentoRequest(
        @NotBlank @Size(max = 150) String descricao,
        @Size(max = 120) String pessoa,
        @NotNull @DecimalMin(value = "0.01", message = "deve ser maior que zero")
        @Digits(integer = 12, fraction = 2) BigDecimal valor,
        @NotNull TipoLancamento tipo,
        @NotNull LocalDate dataVencimento,
        @NotNull Long categoriaId
) {
}
