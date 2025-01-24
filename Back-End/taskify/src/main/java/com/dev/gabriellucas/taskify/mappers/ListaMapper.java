package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.ListaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Lista;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ListaMapper {
     ListaResponseDTO map(Lista lista);
     List<ListaResponseDTO> toDTOListLista(List<Lista> listas);
}
