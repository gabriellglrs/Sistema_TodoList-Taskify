package com.dev.gabriellucas.taskify.services.impl;

import com.dev.gabriellucas.taskify.DTO.ClientRequestDTO;
import com.dev.gabriellucas.taskify.DTO.ClientResponseDTO;
import com.dev.gabriellucas.taskify.entities.Client;
import com.dev.gabriellucas.taskify.mappers.ClientMapper;
import com.dev.gabriellucas.taskify.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl {
     private final ClientRepository clientRepository;
     private final ClientMapper clientMapper;
     private final PasswordEncoder passwordEncoder;

     public ClientResponseDTO Salvar(ClientRequestDTO request){
          Client client = clientMapper.toEntity(request);
          client.setClientSecret(passwordEncoder.encode(request.getClientSecret()));
          return clientMapper.toDTO(clientRepository.save(client));
     }

     public ClientResponseDTO BuscarPorIdDTO(String clientId){
          return clientMapper.toDTO(clientRepository.findByClientId(clientId)) ;
     }

     public Client BuscarPorId(String clientId){
          return clientRepository.findByClientId(clientId);
     }
}
