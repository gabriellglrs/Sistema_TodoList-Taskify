CREATE TABLE tb_categoria (
    id        BIGINT NOT NULL AUTO_INCREMENT,
    cor       VARCHAR(255),
    descricao VARCHAR(255),
    nome      VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB;
