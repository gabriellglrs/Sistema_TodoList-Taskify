package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Lista;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
     List<Tarefa> findByLista(Lista lista);
}
