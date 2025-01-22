CREATE TABLE tb_usuario (
    data_cadastro DATETIME(6),
    id            BIGINT NOT NULL AUTO_INCREMENT,
    email         VARCHAR(255),
    nome          VARCHAR(255),
    senha         VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB;