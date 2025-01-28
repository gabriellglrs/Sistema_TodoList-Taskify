package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.CategoriaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.CategoriaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
     Categoria toEntity(CategoriaRequestDTO requestDTO);
     CategoriaResponseDTO toDTO(Categoria categoria);
     List<CategoriaResponseDTO> toListDTO(List<Categoria> categoriasList);
     void updateEntityFromDTO(CategoriaRequestDTO categoriaRequestDTO, @MappingTarget Categoria categoria);
}
