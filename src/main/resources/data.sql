-- Dados de exemplo carregados no perfil padrão (H2)
INSERT INTO categoria (nome, tipo) VALUES ('Vendas', 'RECEITA');
INSERT INTO categoria (nome, tipo) VALUES ('Serviços de instalação', 'RECEITA');
INSERT INTO categoria (nome, tipo) VALUES ('Fornecedores', 'DESPESA');
INSERT INTO categoria (nome, tipo) VALUES ('Aluguel', 'DESPESA');
INSERT INTO categoria (nome, tipo) VALUES ('Folha de pagamento', 'DESPESA');

INSERT INTO lancamento (descricao, pessoa, valor, tipo, data_vencimento, data_pagamento, categoria_id)
VALUES ('Venda de porcelanato - pedido 101', 'Construtora Alfa', 18500.00, 'RECEITA', DATEADD('DAY', -10, CURRENT_DATE), DATEADD('DAY', -9, CURRENT_DATE), 1);
INSERT INTO lancamento (descricao, pessoa, valor, tipo, data_vencimento, data_pagamento, categoria_id)
VALUES ('Instalação de pedras - obra Jardins', 'Maria Souza', 6200.00, 'RECEITA', DATEADD('DAY', 5, CURRENT_DATE), NULL, 2);
INSERT INTO lancamento (descricao, pessoa, valor, tipo, data_vencimento, data_pagamento, categoria_id)
VALUES ('Venda de mármore - pedido 102', 'Arq. Paulo Lima', 9400.00, 'RECEITA', DATEADD('DAY', -3, CURRENT_DATE), NULL, 1);
INSERT INTO lancamento (descricao, pessoa, valor, tipo, data_vencimento, data_pagamento, categoria_id)
VALUES ('Compra de chapas de granito', 'Pedreira Sul', 11300.00, 'DESPESA', DATEADD('DAY', -5, CURRENT_DATE), DATEADD('DAY', -5, CURRENT_DATE), 3);
INSERT INTO lancamento (descricao, pessoa, valor, tipo, data_vencimento, data_pagamento, categoria_id)
VALUES ('Aluguel do showroom', 'Imobiliária Centro', 7500.00, 'DESPESA', DATEADD('DAY', 8, CURRENT_DATE), NULL, 4);
INSERT INTO lancamento (descricao, pessoa, valor, tipo, data_vencimento, data_pagamento, categoria_id)
VALUES ('Salários do mês', NULL, 15800.00, 'DESPESA', DATEADD('DAY', -1, CURRENT_DATE), NULL, 5);
