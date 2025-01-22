CREATE TABLE tb_notificacao (
    data_envio DATETIME(6),
    id         BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT,
    mensagem   VARCHAR(255),
    titulo     VARCHAR(255),
    status     ENUM ('ARQUIVADA', 'LIDA', 'NAO_LIDA'),
    PRIMARY KEY (id)
) ENGINE = InnoDB;