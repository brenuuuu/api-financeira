package com.brenuuuu.financeiro.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResumoResponse(
        LocalDate inicio,
        LocalDate fim,
        BigDecimal totalReceitas,
        BigDecimal totalDespesas,
        BigDecimal saldo,
        BigDecimal emAtraso
) {
}
