package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Lista;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import com.dev.gabriellucas.taskify.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Long> {
     List<Lista> findByUsuario(Usuario usuario);
}
