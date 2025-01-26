package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.AnexoResponseDTO;
import com.dev.gabriellucas.taskify.entities.Anexo;
import com.dev.gabriellucas.taskify.entities.Comentario;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AnexoMapper {

    @Named("toDTO")
    AnexoResponseDTO toDto(Anexo anexo);
}
