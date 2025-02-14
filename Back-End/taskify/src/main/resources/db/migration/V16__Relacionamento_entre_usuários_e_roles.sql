CREATE TABLE tb_usuario_role (
                                 usuario_id BIGINT NOT NULL,
                                 role_id BIGINT NOT NULL,
                                 PRIMARY KEY (usuario_id, role_id),
                                 FOREIGN KEY (usuario_id) REFERENCES tb_usuario(id) ON DELETE CASCADE,
                                 FOREIGN KEY (role_id) REFERENCES tb_role(id) ON DELETE CASCADE
) ENGINE = InnoDB;