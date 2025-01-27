package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.TarefaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.TarefaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    Tarefa toEntity(TarefaRequestDTO request);

    @Named("toDTO")
    TarefaResponseDTO toDto(Tarefa tarefa);

    List<TarefaResponseDTO> toListDto(List<Tarefa> tarefa);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    void updateEntityFromDTO(TarefaRequestDTO requestDTO, @MappingTarget Tarefa tarefa);
}
