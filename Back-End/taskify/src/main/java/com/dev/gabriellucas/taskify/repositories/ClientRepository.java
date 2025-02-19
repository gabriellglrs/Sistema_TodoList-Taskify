package com.dev.gabriellucas.taskify.repositories;

import com.dev.gabriellucas.taskify.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
     Client findByClientId(String clientId);
}
