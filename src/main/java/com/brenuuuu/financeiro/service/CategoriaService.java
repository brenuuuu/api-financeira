package com.brenuuuu.financeiro.service;

import com.brenuuuu.financeiro.dto.CategoriaRequest;
import com.brenuuuu.financeiro.dto.CategoriaResponse;
import com.brenuuuu.financeiro.exception.RecursoNaoEncontradoException;
import com.brenuuuu.financeiro.exception.RegraDeNegocioException;
import com.brenuuuu.financeiro.model.Categoria;
import com.brenuuuu.financeiro.repository.CategoriaRepository;
import com.brenuuuu.financeiro.repository.LancamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;
    private final LancamentoRepository lancamentoRepository;

    public CategoriaService(CategoriaRepository repository, LancamentoRepository lancamentoRepository) {
        this.repository = repository;
        this.lancamentoRepository = lancamentoRepository;
    }

    public List<CategoriaResponse> listar() {
        return repository.findAll().stream().map(CategoriaResponse::de).toList();
    }

    public CategoriaResponse criar(CategoriaRequest dados) {
        if (repository.existsByNomeIgnoreCase(dados.nome())) {
            throw new RegraDeNegocioException("Já existe uma categoria com o nome " + dados.nome());
        }
        Categoria nova = new Categoria(dados.nome(), dados.tipo());
        return CategoriaResponse.de(repository.save(nova));
    }

    public void excluir(Long id) {
        Categoria categoria = buscarPorId(id);
        if (lancamentoRepository.existsByCategoriaId(id)) {
            throw new RegraDeNegocioException("Categoria tem lançamentos e não pode ser excluída");
        }
        repository.delete(categoria);
    }

    public Categoria buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria " + id + " não encontrada"));
    }
}
