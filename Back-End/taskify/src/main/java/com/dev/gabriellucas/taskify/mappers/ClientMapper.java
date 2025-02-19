package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.ClientRequestDTO;
import com.dev.gabriellucas.taskify.DTO.ClientResponseDTO;
import com.dev.gabriellucas.taskify.entities.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
     Client toEntity(ClientRequestDTO requestDTO);
     ClientResponseDTO toDTO(Client client);
}
