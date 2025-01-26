package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Anexo;
import com.dev.gabriellucas.taskify.entities.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long> {

    List<Anexo> findAllByTarefaId(Long tarefaId);
}
