package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
