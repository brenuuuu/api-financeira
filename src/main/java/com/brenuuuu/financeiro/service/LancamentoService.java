package com.brenuuuu.financeiro.service;

import com.brenuuuu.financeiro.dto.LancamentoRequest;
import com.brenuuuu.financeiro.dto.LancamentoResponse;
import com.brenuuuu.financeiro.exception.RecursoNaoEncontradoException;
import com.brenuuuu.financeiro.exception.RegraDeNegocioException;
import com.brenuuuu.financeiro.model.Categoria;
import com.brenuuuu.financeiro.model.Lancamento;
import com.brenuuuu.financeiro.model.StatusLancamento;
import com.brenuuuu.financeiro.model.TipoLancamento;
import com.brenuuuu.financeiro.repository.LancamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LancamentoService {

    private final LancamentoRepository repository;
    private final CategoriaService categoriaService;

    public LancamentoService(LancamentoRepository repository, CategoriaService categoriaService) {
        this.repository = repository;
        this.categoriaService = categoriaService;
    }

    public List<LancamentoResponse> listar(TipoLancamento tipo, StatusLancamento status) {
        List<Lancamento> lista = (tipo == null)
                ? repository.findAll()
                : repository.findByTipoOrderByDataVencimento(tipo);

        return lista.stream()
                .filter(l -> status == null || l.getStatus() == status)
                .map(LancamentoResponse::de)
                .toList();
    }

    public LancamentoResponse buscar(Long id) {
        return LancamentoResponse.de(buscarPorId(id));
    }

    public LancamentoResponse criar(LancamentoRequest dados) {
        Categoria categoria = buscarCategoriaDoMesmoTipo(dados);
        Lancamento novo = new Lancamento(dados.descricao(), dados.pessoa(), dados.valor(),
                dados.tipo(), dados.dataVencimento(), categoria);
        return LancamentoResponse.de(repository.save(novo));
    }

    public LancamentoResponse atualizar(Long id, LancamentoRequest dados) {
        Lancamento lancamento = buscarPorId(id);
        if (lancamento.getDataPagamento() != null) {
            throw new RegraDeNegocioException("Lançamento já pago não pode ser alterado");
        }
        lancamento.setDescricao(dados.descricao());
        lancamento.setPessoa(dados.pessoa());
        lancamento.setValor(dados.valor());
        lancamento.setTipo(dados.tipo());
        lancamento.setDataVencimento(dados.dataVencimento());
        lancamento.setCategoria(buscarCategoriaDoMesmoTipo(dados));
        return LancamentoResponse.de(repository.save(lancamento));
    }

    /** Marca a conta como paga (ou recebida) na data de hoje. */
    public LancamentoResponse pagar(Long id) {
        Lancamento lancamento = buscarPorId(id);
        if (lancamento.getDataPagamento() != null) {
            throw new RegraDeNegocioException("Este lançamento já foi pago");
        }
        lancamento.setDataPagamento(LocalDate.now());
        return LancamentoResponse.de(repository.save(lancamento));
    }

    public void excluir(Long id) {
        repository.delete(buscarPorId(id));
    }

    // Regra: uma despesa só pode usar categoria de despesa (e o mesmo para receita)
    private Categoria buscarCategoriaDoMesmoTipo(LancamentoRequest dados) {
        Categoria categoria = categoriaService.buscarPorId(dados.categoriaId());
        if (categoria.getTipo() != dados.tipo()) {
            throw new RegraDeNegocioException("A categoria '" + categoria.getNome()
                    + "' é de " + categoria.getTipo() + " e o lançamento é de " + dados.tipo());
        }
        return categoria;
    }

    private Lancamento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lançamento " + id + " não encontrado"));
    }
}
