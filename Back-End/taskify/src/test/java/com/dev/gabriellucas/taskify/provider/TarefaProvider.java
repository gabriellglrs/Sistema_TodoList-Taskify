package com.dev.gabriellucas.taskify.provider;

import com.dev.gabriellucas.taskify.entities.Lista;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import com.dev.gabriellucas.taskify.enums.PrioridadeTarefa;
import com.dev.gabriellucas.taskify.enums.StatusTarefa;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public class TarefaProvider {

    public static final Long ID = 1L;
    public static final String TITULO = "Guardar o carro";
    public static final String DESCRICAO = "Tirar o carro da rua";
    public static final LocalDateTime DATA_CRIACAO = LocalDateTime.now();
    public static final LocalDateTime DATA_VENCIMENTO = LocalDateTime.now().plusDays(2);
    public static final StatusTarefa STATUS_TAREFA = StatusTarefa.PENDENTE;
    public static final PrioridadeTarefa PRIORIDADE_TAREFA = PrioridadeTarefa.URGENTE;

    public Tarefa create() {

        Tarefa tarefa = new Tarefa();
        tarefa.setId(ID);
        tarefa.setTitulo(TITULO);
        tarefa.setDescricao(DESCRICAO);
        tarefa.setDataCriacao(DATA_CRIACAO);
        tarefa.setDataVencimento(DATA_VENCIMENTO);
        tarefa.setStatus(STATUS_TAREFA);
        tarefa.setPrioridade(PRIORIDADE_TAREFA);
        Lista lista = new Lista();
        lista.setId(ID);
        tarefa.setLista(lista);
        lista.getTarefas().add(tarefa);
        return tarefa;
    }
}
