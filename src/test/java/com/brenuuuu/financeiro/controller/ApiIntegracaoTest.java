package com.brenuuuu.financeiro.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Sobe a aplicação com os dados de exemplo (data.sql) e testa os endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApiIntegracaoTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void listaOsLancamentosDeExemplo() throws Exception {
        mvc.perform(get("/api/lancamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    void criaUmLancamento() throws Exception {
        String json = """
                {"descricao": "Conta de energia", "pessoa": "Enel", "valor": 850.40,
                 "tipo": "DESPESA", "dataVencimento": "2030-01-10", "categoriaId": 4}
                """;

        mvc.perform(post("/api/lancamentos").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    void pagaUmLancamento() throws Exception {
        mvc.perform(patch("/api/lancamentos/2/pagar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAGO"));
    }

    @Test
    void naoAceitaCategoriaDeOutroTipo() throws Exception {
        // categoria 4 (Aluguel) é de DESPESA, mas o lançamento é RECEITA
        String json = """
                {"descricao": "Teste", "valor": 10, "tipo": "RECEITA",
                 "dataVencimento": "2030-01-10", "categoriaId": 4}
                """;

        mvc.perform(post("/api/lancamentos").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void retorna404QuandoNaoExiste() throws Exception {
        mvc.perform(get("/api/lancamentos/999")).andExpect(status().isNotFound());
    }
}
