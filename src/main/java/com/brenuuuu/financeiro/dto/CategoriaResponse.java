package com.brenuuuu.financeiro.dto;

import com.brenuuuu.financeiro.model.Categoria;
import com.brenuuuu.financeiro.model.TipoLancamento;

public record CategoriaResponse(Long id, String nome, TipoLancamento tipo) {

    public static CategoriaResponse de(Categoria c) {
        return new CategoriaResponse(c.getId(), c.getNome(), c.getTipo());
    }
}
