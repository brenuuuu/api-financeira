# API Financeira 💰

![CI](https://github.com/brenuuuu/api-financeira/actions/workflows/ci.yml/badge.svg)

API REST de **contas a pagar e a receber**, feita com Java e Spring Boot para praticar back-end.

A ideia veio do meu trabalho no setor financeiro, onde acompanho pagamentos a fornecedores e recebimentos de clientes. Quis entender como seria um sistema simples para controlar isso.

## O que ela faz

- Cadastra **categorias** (ex.: Vendas, Aluguel, Fornecedores)
- Cadastra **lançamentos** (contas a pagar ou a receber)
- Mostra o **status** de cada conta: `PENDENTE`, `PAGO` ou `ATRASADO`
- Permite **marcar uma conta como paga**
- Gera um **resumo do período**: total de receitas, despesas, saldo e valor em atraso
- Valida os dados (ex.: valor tem que ser maior que zero) e devolve erros em JSON

## Tecnologias

- Java 17
- Spring Boot 3 (Web, Data JPA, Validation)
- Banco H2 (em memória)
- Swagger para testar os endpoints
- JUnit para os testes

## Como rodar

Precisa ter o **Java 17** e o **Maven** instalados (ou abrir o projeto no IntelliJ e rodar a classe `ApiFinanceiraApplication`).

```bash
git clone https://github.com/brenuuuu/api-financeira.git
cd api-financeira
mvn spring-boot:run
```

A API sobe em `http://localhost:8080` já com alguns dados de exemplo.

- Swagger (para testar pelo navegador): http://localhost:8080/swagger-ui.html
- Console do banco H2: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:financeiro`, usuário: `sa`)

Para rodar os testes:

```bash
mvn test
```

## Endpoints

| Método | Rota | O que faz |
|---|---|---|
| GET | `/api/categorias` | Lista as categorias |
| POST | `/api/categorias` | Cria uma categoria |
| DELETE | `/api/categorias/{id}` | Exclui uma categoria |
| GET | `/api/lancamentos` | Lista os lançamentos (filtros opcionais: `tipo` e `status`) |
| GET | `/api/lancamentos/{id}` | Busca um lançamento |
| POST | `/api/lancamentos` | Cria um lançamento |
| PUT | `/api/lancamentos/{id}` | Altera um lançamento |
| PATCH | `/api/lancamentos/{id}/pagar` | Marca como pago |
| DELETE | `/api/lancamentos/{id}` | Exclui um lançamento |
| GET | `/api/resumo?inicio=...&fim=...` | Resumo do período |

Tem exemplos prontos no arquivo [`requisicoes.http`](requisicoes.http).

**Exemplo de cadastro:**

```json
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

## Organização do código

```
controller  → recebe as requisições e devolve as respostas
service     → regras de negócio
repository  → acesso ao banco de dados
model       → entidades (Categoria e Lancamento)
dto         → dados que entram e saem da API
exception   → tratamento de erros
```

## O que aprendi

- Montar uma API REST com Spring Boot separando em camadas
- Usar JPA para salvar e buscar no banco sem escrever SQL na mão
- Validar dados de entrada e tratar erros
- Escrever testes com JUnit e MockMvc

## Próximos passos

- [ ] Trocar o H2 por PostgreSQL
- [ ] Adicionar login com Spring Security
- [ ] Contas que se repetem todo mês (ex.: aluguel)

---

Feito por **Breno Oliveira**, estudante de Análise e Desenvolvimento de Sistemas na UNICID.
