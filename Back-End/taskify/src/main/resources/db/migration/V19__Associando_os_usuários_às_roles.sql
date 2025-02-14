-- Associando os usuários às roles
-- Supondo que o ID dos usuários inseridos seja de 1 a 5
INSERT INTO tb_usuario_role (usuario_id, role_id) VALUES
                                                      (6, 1), -- João com ROLE_USER
                                                      (7, 2), -- Maria com ROLE_ADMIN
                                                      (8, 1), -- Pedro com ROLE_USER
                                                      (8, 2); -- Pedro com ROLE_ADMIN

