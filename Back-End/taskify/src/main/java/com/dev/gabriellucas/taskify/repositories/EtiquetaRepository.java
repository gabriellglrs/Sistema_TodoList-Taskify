package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Anexo;
import com.dev.gabriellucas.taskify.entities.Etiqueta;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long> {
    @Query("SELECT COUNT(e) > 0 FROM Etiqueta e WHERE LOWER(e.nome) = LOWER(:nome) AND :tarefa MEMBER OF e.tarefas AND e.id != :id")
    boolean existsEtiquetaWithNomeInTarefa(@Param("nome") String nome, @Param("tarefa") Tarefa tarefa, @Param("id") Long id);
    @Query("SELECT COUNT(e) FROM Etiqueta e")
    int countEtiquetas();
    List<Etiqueta> findAllByTarefasId(Long tarefaId);
}
