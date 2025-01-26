package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.ComentarioResponseDTO;
import com.dev.gabriellucas.taskify.DTO.TarefaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Comentario;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    @Named("toDTO")
    ComentarioResponseDTO toDto(Comentario comentario);
}
