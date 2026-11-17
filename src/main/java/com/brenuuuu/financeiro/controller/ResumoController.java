package com.brenuuuu.financeiro.controller;

import com.brenuuuu.financeiro.dto.ResumoResponse;
import com.brenuuuu.financeiro.service.ResumoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/resumo")
public class ResumoController {

    private final ResumoService service;

    public ResumoController(ResumoService service) {
        this.service = service;
    }

    // Exemplo: /api/resumo?inicio=2026-11-01&fim=2026-11-30
    @GetMapping
    public ResumoResponse resumo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return service.gerar(inicio, fim);
    }
}
