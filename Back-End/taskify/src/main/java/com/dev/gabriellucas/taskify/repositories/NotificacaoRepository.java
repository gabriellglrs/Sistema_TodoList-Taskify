package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Notificacao;
import com.dev.gabriellucas.taskify.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
     List<Notificacao> findByUsuario(Usuario usuario);
}
