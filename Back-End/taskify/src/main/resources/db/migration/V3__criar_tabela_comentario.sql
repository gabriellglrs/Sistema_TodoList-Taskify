CREATE TABLE tb_comentario (
    data_criacao DATETIME(6),
    id           BIGINT NOT NULL AUTO_INCREMENT,
    tarefa_id    BIGINT,
    texto        VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB;