package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Long> {

    List<Historico> findAllByTarefaId(Long tarefaId);
}
