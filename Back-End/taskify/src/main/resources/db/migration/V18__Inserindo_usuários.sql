-- Inserindo usuários com senhas criptografadas
INSERT INTO tb_usuario (data_cadastro, email, nome, senha) VALUES
                                                               ('2024-01-22 10:00:00', 'joao@email.com', 'João Silva', '$2a$10$iI8nR2p5RTTbfYBc/m0Qb.ZDutJE1sxrJJz2q2pflrBijJYcABx1q'), -- 123 $2a$10$iI8nR2p5RTTbfYBc/m0Qb.ZDutJE1sxrJJz2q2pflrBijJYcABx1q
                                                               ('2024-01-22 10:30:00', 'maria@email.com', 'Maria Santos', '$2a$10$SfWzOdm0SL3K6Qe1iP1vnu0q8rO7T45Gx9B5xANwnL0OIMfN8.vNK'), -- 456
                                                               ('2024-01-22 11:00:00', 'pedro@email.com', 'Pedro Oliveira', '$2a$10$r7b/3YHyO4eJgy8fE74/sOvBj2/rq2Igb4Snm6YXrS1PmlDREb9nG'); -- 789
