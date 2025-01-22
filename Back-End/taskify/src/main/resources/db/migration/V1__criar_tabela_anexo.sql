CREATE TABLE tb_anexo (
    id        BIGINT NOT NULL AUTO_INCREMENT,
    tamanho   BIGINT,
    tarefa_id BIGINT,
    nome      VARCHAR(255),
    tipo      VARCHAR(255),
    url       VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB;