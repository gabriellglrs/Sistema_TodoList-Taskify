CREATE TABLE tb_tarefa_categoria (
    categoria_id BIGINT NOT NULL,
    tarefa_id    BIGINT NOT NULL,
    PRIMARY KEY (categoria_id, tarefa_id)
) ENGINE = InnoDB;