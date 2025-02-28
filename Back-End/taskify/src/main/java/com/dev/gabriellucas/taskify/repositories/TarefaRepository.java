package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Categoria;
import com.dev.gabriellucas.taskify.entities.Lista;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
   // Conta o número de tarefas associadas a uma lista específica
    @Query("SELECT COUNT(t) FROM Tarefa t WHERE t.lista.id = :id")
    int countTarefasByListaId(@Param("id") Long id);

    // Encontra todas as tarefas associadas a uma lista específica
    List<Tarefa> findByLista(Lista lista);

}
