CREATE TABLE tb_lista (
    data_criacao DATETIME(6),
    id           BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id   BIGINT,
    descricao    VARCHAR(255),
    titulo       VARCHAR(255),
    status       ENUM ('ARQUIVADA', 'ATIVA', 'EXCLUIDA'),
    PRIMARY KEY (id)
) ENGINE = InnoDB;