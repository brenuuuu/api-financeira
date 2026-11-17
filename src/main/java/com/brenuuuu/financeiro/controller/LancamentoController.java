package com.brenuuuu.financeiro.controller;

import com.brenuuuu.financeiro.dto.LancamentoRequest;
import com.brenuuuu.financeiro.dto.LancamentoResponse;
import com.brenuuuu.financeiro.model.StatusLancamento;
import com.brenuuuu.financeiro.model.TipoLancamento;
import com.brenuuuu.financeiro.service.LancamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    private final LancamentoService service;

    public LancamentoController(LancamentoService service) {
        this.service = service;
    }

    // Exemplos: /api/lancamentos?tipo=DESPESA  ou  /api/lancamentos?status=ATRASADO
    @GetMapping
    public List<LancamentoResponse> listar(@RequestParam(required = false) TipoLancamento tipo,
                                           @RequestParam(required = false) StatusLancamento status) {
        return service.listar(tipo, status);
    }

    @GetMapping("/{id}")
    public LancamentoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoResponse criar(@RequestBody @Valid LancamentoRequest dados) {
        return service.criar(dados);
    }

    @PutMapping("/{id}")
    public LancamentoResponse atualizar(@PathVariable Long id, @RequestBody @Valid LancamentoRequest dados) {
        return service.atualizar(id, dados);
    }

    @PatchMapping("/{id}/pagar")
    public LancamentoResponse pagar(@PathVariable Long id) {
        return service.pagar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
