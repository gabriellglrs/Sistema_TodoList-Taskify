CREATE TABLE tb_tarefa (
    data_criacao    DATETIME(6),
    data_vencimento DATETIME(6),
    id              BIGINT NOT NULL AUTO_INCREMENT,
    lista_id        BIGINT,
    descricao       VARCHAR(255),
    titulo          VARCHAR(255),
    prioridade      ENUM ('ALTA', 'BAIXA', 'MEDIA', 'URGENTE'),
    status          ENUM ('CANCELADA', 'CONCLUIDA', 'EM_ANDAMENTO', 'PENDENTE'),
    PRIMARY KEY (id)
) ENGINE = InnoDB;
