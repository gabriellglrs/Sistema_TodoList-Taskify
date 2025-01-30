package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.CategoriaIdDTO;
import com.dev.gabriellucas.taskify.DTO.CategoriaResponseDTO;
import com.dev.gabriellucas.taskify.DTO.TarefaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.TarefaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Categoria;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    Tarefa toEntity(TarefaRequestDTO request);

    @Mapping(target = "categoria", expression = "java(mapCategorias(tarefa.getCategorias()))")
    TarefaResponseDTO toDto(Tarefa tarefa);

    List<TarefaResponseDTO> toListDto(List<Tarefa> tarefa);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    void updateEntityFromDTO(TarefaRequestDTO requestDTO, @MappingTarget Tarefa tarefa);

    default List<CategoriaIdDTO> mapCategorias(Set<Categoria> categorias) {
        if (categorias == null) {
            return null;
        }
        return categorias.stream()
                .map(categoria -> new CategoriaIdDTO(categoria.getId()))
                .collect(Collectors.toList());
    }
}
