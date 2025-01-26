package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.HistoricoResponseDTO;
import com.dev.gabriellucas.taskify.entities.Historico;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface HistoricoMapper {

    @Named("toDTO")
    HistoricoResponseDTO toDto(Historico historico);
}
