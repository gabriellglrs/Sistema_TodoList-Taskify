CREATE TABLE tb_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    authority VARCHAR(50) NOT NULL UNIQUE
) ENGINE = InnoDB;