CREATE TABLE tb_tarefa_etiqueta (
    etiqueta_id BIGINT NOT NULL,
    tarefa_id   BIGINT NOT NULL,
    PRIMARY KEY (etiqueta_id, tarefa_id)
) ENGINE = InnoDB;