package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findAllByTarefaId(Long tarefaId);
}
