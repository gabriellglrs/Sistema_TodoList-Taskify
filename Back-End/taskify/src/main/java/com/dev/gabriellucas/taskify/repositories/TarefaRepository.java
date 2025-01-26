package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}
