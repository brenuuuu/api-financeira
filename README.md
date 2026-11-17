# API Financeira 💰

![CI](https://github.com/brenuuuu/api-financeira/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen)

API REST para **controle de contas a pagar e a receber** de uma pequena empresa: cadastro de lançamentos, baixa de pagamentos, contas em atraso e um resumo financeiro do período (previsto x realizado).

A ideia veio da minha rotina no setor financeiro de uma empresa de revestimentos, onde acompanho recebimentos de clientes, pagamentos a fornecedores e o fluxo de caixa do mês.

## Funcionalidades

- CRUD de **categorias** (receita ou despesa)
- CRUD de **lançamentos** com validação dos dados
- **Status automático**: `PENDENTE`, `PAGO` ou `ATRASADO` (calculado pela data de vencimento)
- **Baixa** e **estorno** de pagamentos
- Filtros por tipo, status e período
- **Resumo do período**: total de receitas e despesas, saldo previsto e realizado, valor em atraso e total por categoria
- Erros padronizados em JSON (Problem Details, RFC 7807)
- Documentação interativa com **Swagger**
- Testes automatizados (JUnit 5 + MockMvc) rodando no **GitHub Actions**

## Tecnologias

Java 17 · Spring Boot 3 · Spring Data JPA · Bean Validation · H2 · PostgreSQL · Springdoc OpenAPI · JUnit 5 · Docker

## Arquitetura

```
controller  →  recebe as requisições HTTP e devolve JSON
service     →  regras de negócio (ex.: categoria precisa ser do mesmo tipo do lançamento)
repository  →  acesso ao banco com Spring Data JPA
model       →  entidades (Categoria, Lancamento)
dto         →  objetos de entrada e saída da API (records)
exception   →  tratamento centralizado de erros
```

## Como rodar

**Pré-requisitos:** Java 17+ e Maven.

```bash
git clone https://github.com/brenuuuu/api-financeira.git
cd api-financeira
mvn spring-boot:run
```

A API sobe em `http://localhost:8080` com um banco H2 em memória e **dados de exemplo** já carregados.

- Swagger: http://localhost:8080/swagger-ui.html
- Console do H2: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:financeiro`, usuário `sa`)

### Com PostgreSQL (Docker)

```bash
docker compose up --build
```

### Rodar os testes

```bash
mvn test
```

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/categorias` | Lista categorias |
| POST | `/api/categorias` | Cria categoria |
| PUT | `/api/categorias/{id}` | Atualiza categoria |
| DELETE | `/api/categorias/{id}` | Exclui (se não tiver lançamentos) |
| GET | `/api/lancamentos?tipo=&status=&inicio=&fim=` | Lista com filtros |
| GET | `/api/lancamentos/{id}` | Busca um lançamento |
| POST | `/api/lancamentos` | Cria lançamento |
| PUT | `/api/lancamentos/{id}` | Atualiza (se ainda não foi baixado) |
| PATCH | `/api/lancamentos/{id}/baixa` | Marca como pago/recebido |
| PATCH | `/api/lancamentos/{id}/estorno` | Desfaz a baixa |
| DELETE | `/api/lancamentos/{id}` | Exclui |
| GET | `/api/resumo?inicio=&fim=` | Resumo financeiro do período |

Mais exemplos prontos no arquivo [`requisicoes.http`](requisicoes.http).

### Exemplo: criar uma conta a pagar

```http
POST /api/lancamentos
{
  "descricao": "Conta de energia",
  "pessoa": "Enel",
  "valor": 850.40,
  "tipo": "DESPESA",
  "dataVencimento": "2026-12-10",
  "categoriaId": 4
}
```

### Exemplo: resposta do resumo

```json
{
  "inicio": "2026-11-01",
  "fim": "2026-11-30",
  "totalReceitas": 34100.00,
  "totalDespesas": 34600.00,
  "saldoPrevisto": -500.00,
  "recebido": 18500.00,
  "pago": 11300.00,
  "saldoRealizado": 7200.00,
  "emAtraso": 25200.00,
  "porCategoria": [
    { "categoria": "Vendas", "tipo": "RECEITA", "total": 27900.00 }
  ]
}
```

## Próximos passos

- [ ] Autenticação com Spring Security + JWT
- [ ] Lançamentos recorrentes (ex.: aluguel todo mês)
- [ ] Paginação na listagem
- [ ] Exportar relatório em CSV

## Autor

**Breno Oliveira** — estudante de Análise e Desenvolvimento de Sistemas (UNICID)
[GitHub](https://github.com/brenuuuu)
