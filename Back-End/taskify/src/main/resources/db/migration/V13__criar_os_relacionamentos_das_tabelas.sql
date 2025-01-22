ALTER TABLE tb_permissao
    ADD CONSTRAINT UK_permissao_lista UNIQUE (lista_id);
ALTER TABLE tb_anexo
    ADD CONSTRAINT FK_anexo_tarefa FOREIGN KEY (tarefa_id) REFERENCES tb_tarefa (id);
ALTER TABLE tb_comentario
    ADD CONSTRAINT FK_comentario_tarefa FOREIGN KEY (tarefa_id) REFERENCES tb_tarefa (id);
ALTER TABLE tb_historico
    ADD CONSTRAINT FK_historico_tarefa FOREIGN KEY (tarefa_id) REFERENCES tb_tarefa (id);
ALTER TABLE tb_lista
    ADD CONSTRAINT FK_lista_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id);
ALTER TABLE tb_notificacao
    ADD CONSTRAINT FK_notificacao_usuario FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id);
ALTER TABLE tb_permissao
    ADD CONSTRAINT FK_permissao_lista FOREIGN KEY (lista_id) REFERENCES tb_lista (id);
ALTER TABLE tb_tarefa
    ADD CONSTRAINT FK_tarefa_lista FOREIGN KEY (lista_id) REFERENCES tb_lista (id);
ALTER TABLE tb_tarefa_categoria
    ADD CONSTRAINT FK_tarefacategoria_categoria FOREIGN KEY (categoria_id) REFERENCES tb_categoria (id);
ALTER TABLE tb_tarefa_categoria
    ADD CONSTRAINT FK_tarefacategoria_tarefa FOREIGN KEY (tarefa_id) REFERENCES tb_tarefa (id);
ALTER TABLE tb_tarefa_etiqueta
    ADD CONSTRAINT FK_tarefaetiqueta_etiqueta FOREIGN KEY (etiqueta_id) REFERENCES tb_etiqueta (id);
ALTER TABLE tb_tarefa_etiqueta
    ADD CONSTRAINT FK_tarefaetiqueta_terefa FOREIGN KEY (tarefa_id) REFERENCES tb_tarefa (id);