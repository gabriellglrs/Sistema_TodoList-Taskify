package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
