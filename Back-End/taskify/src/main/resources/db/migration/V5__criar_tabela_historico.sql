CREATE TABLE tb_historico (
    data_modificacao DATETIME(6),
    id               BIGINT NOT NULL AUTO_INCREMENT,
    tarefa_id        BIGINT,
    acao             VARCHAR(255),
    descricao        VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB;