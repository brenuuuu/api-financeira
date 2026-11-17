package com.brenuuuu.financeiro.dto;

import com.brenuuuu.financeiro.model.TipoLancamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
        @NotBlank @Size(max = 80) String nome,
        @NotNull TipoLancamento tipo
) {
}
