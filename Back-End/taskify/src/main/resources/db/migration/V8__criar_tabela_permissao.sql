CREATE TABLE tb_permissao (
    id       BIGINT NOT NULL AUTO_INCREMENT,
    lista_id BIGINT,
    nivel    ENUM ('BASICO', 'COMPLETO', 'INTERMEDIARIO'),
    tipo     ENUM ('ADMINISTRACAO', 'EDICAO', 'VISUALIZACAO'),
    PRIMARY KEY (id)
) ENGINE = InnoDB;