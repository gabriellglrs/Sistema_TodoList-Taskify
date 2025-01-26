package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.ListaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.TarefaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.TarefaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Lista;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    Tarefa toEntity(TarefaRequestDTO request);

    @Named("toDTO")
    TarefaResponseDTO toDto(Tarefa tarefa);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    void updateEntityFromDTO(TarefaRequestDTO requestDTO, @MappingTarget Tarefa tarefa);
}
