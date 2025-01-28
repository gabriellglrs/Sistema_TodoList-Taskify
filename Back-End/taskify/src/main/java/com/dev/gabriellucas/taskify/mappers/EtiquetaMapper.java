package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.EtiquetaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.EtiquetaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Etiqueta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EtiquetaMapper {

    Etiqueta toEntity(EtiquetaRequestDTO request);

    @Named("toDTO")
    EtiquetaResponseDTO toDto(Etiqueta etiqueta);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(EtiquetaRequestDTO requestDTO, @MappingTarget Etiqueta etiqueta);

}
