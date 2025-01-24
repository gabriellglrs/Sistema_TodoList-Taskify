-- Inserindo usuários
INSERT INTO tb_usuario (data_cadastro, email, nome, senha) VALUES
                                                               ('2024-01-22 10:00:00', 'joao@email.com', 'João Silva', 'senha123'),
                                                               ('2024-01-22 10:30:00', 'maria@email.com', 'Maria Santos', 'senha456'),
                                                               ('2024-01-22 11:00:00', 'pedro@email.com', 'Pedro Oliveira', 'senha789'),
                                                               ('2024-01-22 11:30:00', 'ana@email.com', 'Ana Souza', 'senha321'),
                                                               ('2024-01-22 12:00:00', 'lucas@email.com', 'Lucas Pereira', 'senha654');

-- Inserindo categorias
INSERT INTO tb_categoria (cor, descricao, nome) VALUES
                                                    ('#FF0000', 'Tarefas relacionadas ao trabalho', 'Trabalho'),
                                                    ('#00FF00', 'Tarefas pessoais', 'Pessoal'),
                                                    ('#0000FF', 'Tarefas de estudo', 'Estudo'),
                                                    ('#FFFF00', 'Tarefas de casa', 'Casa'),
                                                    ('#FF00FF', 'Tarefas de lazer', 'Lazer');

-- Inserindo etiquetas
INSERT INTO tb_etiqueta (cor, nome) VALUES
                                        ('#FFA500', 'Importante'),
                                        ('#800080', 'Urgente'),
                                        ('#008000', 'Em progresso'),
                                        ('#4B0082', 'Revisão'),
                                        ('#FF1493', 'Aguardando');

-- Inserindo listas
INSERT INTO tb_lista (data_criacao, usuario_id, descricao, titulo, STATUS) VALUES
                                                                               ('2024-01-22 13:00:00', 1, 'Lista de tarefas do trabalho', 'Trabalho Q1', 'ATIVA'),
                                                                               ('2024-01-22 13:30:00', 2, 'Lista de tarefas pessoais', 'Pessoal 2024', 'ATIVA'),
                                                                               ('2024-01-22 14:00:00', 3, 'Lista de estudos', 'Estudos 2024', 'ATIVA'),
                                                                               ('2024-01-22 14:30:00', 4, 'Lista de casa', 'Casa 2024', 'ATIVA'),
                                                                               ('2024-01-22 15:00:00', 5, 'Lista de projetos', 'Projetos', 'ATIVA');

-- Inserindo permissões
INSERT INTO tb_permissao (lista_id, nivel, tipo) VALUES
                                                     (1, 'COMPLETO', 'ADMINISTRACAO'),
                                                     (2, 'COMPLETO', 'ADMINISTRACAO'),
                                                     (3, 'COMPLETO', 'ADMINISTRACAO'),
                                                     (4, 'COMPLETO', 'ADMINISTRACAO'),
                                                     (5, 'COMPLETO', 'ADMINISTRACAO');

-- Inserindo tarefas
INSERT INTO tb_tarefa (data_criacao, data_vencimento, lista_id, descricao, titulo, prioridade, STATUS) VALUES
                                                                                                           ('2024-01-22 16:00:00', '2024-02-22 16:00:00', 1, 'Preparar relatório mensal', 'Relatório Janeiro', 'ALTA', 'PENDENTE'),
                                                                                                           ('2024-01-22 16:30:00', '2024-02-23 16:00:00', 2, 'Fazer compras do mês', 'Compras Supermercado', 'MEDIA', 'PENDENTE'),
                                                                                                           ('2024-01-22 17:00:00', '2024-02-24 16:00:00', 3, 'Estudar para prova', 'Prova JavaScript', 'URGENTE', 'EM_ANDAMENTO'),
                                                                                                           ('2024-01-22 17:30:00', '2024-02-25 16:00:00', 4, 'Limpar garagem', 'Limpeza Garagem', 'BAIXA', 'PENDENTE'),
                                                                                                           ('2024-01-22 18:00:00', '2024-02-26 16:00:00', 5, 'Desenvolver novo projeto', 'Projeto X', 'ALTA', 'EM_ANDAMENTO');

-- Inserindo comentários
INSERT INTO tb_comentario (data_criacao, tarefa_id, texto) VALUES
                                                               ('2024-01-22 19:00:00', 1, 'Iniciando o relatório'),
                                                               ('2024-01-22 19:30:00', 2, 'Lista de compras atualizada'),
                                                               ('2024-01-22 20:00:00', 3, 'Material de estudo separado'),
                                                               ('2024-01-22 20:30:00', 4, 'Ferramentas preparadas'),
                                                               ('2024-01-22 21:00:00', 5, 'Definindo escopo do projeto');

-- Inserindo histórico
INSERT INTO tb_historico (data_modificacao, tarefa_id, acao, descricao) VALUES
                                                                            ('2024-01-22 22:00:00', 1, 'CRIACAO', 'Tarefa criada'),
                                                                            ('2024-01-22 22:30:00', 2, 'CRIACAO', 'Tarefa criada'),
                                                                            ('2024-01-22 23:00:00', 3, 'ATUALIZACAO', 'Status atualizado'),
                                                                            ('2024-01-22 23:30:00', 4, 'CRIACAO', 'Tarefa criada'),
                                                                            ('2024-01-23 00:00:00', 5, 'ATUALIZACAO', 'Prioridade atualizada');

-- Inserindo anexos
INSERT INTO tb_anexo (tamanho, tarefa_id, nome, tipo, URL) VALUES
                                                               (1024, 1, 'relatorio.pdf', 'application/pdf', '/anexos/relatorio.pdf'),
                                                               (2048, 2, 'lista.xlsx', 'application/xlsx', '/anexos/lista.xlsx'),
                                                               (512, 3, 'material.pdf', 'application/pdf', '/anexos/material.pdf'),
                                                               (1536, 4, 'foto.jpg', 'image/jpeg', '/anexos/foto.jpg'),
                                                               (4096, 5, 'documento.docx', 'application/docx', '/anexos/documento.docx');

-- Inserindo notificações
INSERT INTO tb_notificacao (data_envio, usuario_id, mensagem, titulo, STATUS) VALUES
                                                                                  ('2024-01-23 01:00:00', 1, 'Seu relatório precisa ser entregue em breve', 'Lembrete de Tarefa', 'NAO_LIDA'),
                                                                                  ('2024-01-23 01:30:00', 2, 'Nova tarefa atribuída', 'Nova Tarefa', 'NAO_LIDA'),
                                                                                  ('2024-01-23 02:00:00', 3, 'Prazo de estudo próximo', 'Lembrete de Estudo', 'NAO_LIDA'),
                                                                                  ('2024-01-23 02:30:00', 4, 'Tarefa de limpeza pendente', 'Lembrete de Tarefa', 'NAO_LIDA'),
                                                                                  ('2024-01-23 03:00:00', 5, 'Novo comentário no projeto', 'Atualização de Projeto', 'NAO_LIDA');

-- Inserindo relacionamentos tarefa-categoria
INSERT INTO tb_tarefa_categoria (categoria_id, tarefa_id) VALUES
                                                              (1, 1),
                                                              (2, 2),
                                                              (3, 3),
                                                              (4, 4),
                                                              (5, 5);

-- Inserindo relacionamentos tarefa-etiqueta
INSERT INTO tb_tarefa_etiqueta (etiqueta_id, tarefa_id) VALUES
                                                            (1, 1),
                                                            (2, 2),
                                                            (3, 3),
                                                            (4, 4),
                                                            (5, 5);