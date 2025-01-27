package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("SELECT COUNT(t) FROM Tarefa t WHERE t.lista.id = :id")
    int countTarefasByListaId(@Param("id") Long id);
}
