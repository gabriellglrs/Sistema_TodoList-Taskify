CREATE TABLE tb_client (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    client_id VARCHAR(150) NOT NULL UNIQUE,
    client_secret VARCHAR(400) NOT NULL,
    redirect_uri VARCHAR(200) NOT NULL ,
    scope VARCHAR(50)
);
