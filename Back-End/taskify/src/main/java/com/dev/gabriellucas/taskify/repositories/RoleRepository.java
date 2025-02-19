package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
