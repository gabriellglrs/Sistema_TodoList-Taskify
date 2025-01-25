package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.ListaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.ListaResponseDTO;
import com.dev.gabriellucas.taskify.DTO.UsuarioRequestDTO;
import com.dev.gabriellucas.taskify.entities.Lista;
import com.dev.gabriellucas.taskify.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ListaMapper {

     Lista toEntity(ListaRequestDTO requestDTO);

     @Named("toDTO")
     ListaResponseDTO toDTO(Lista lista);

     @Named("map")
     ListaResponseDTO map(Lista listaMap);

     List<ListaResponseDTO> toDTOListLista(List<Lista> listas);

     @Mapping(target = "id", ignore = true) // Ignora o ID durante a atualização
     @Mapping(target = "dataCriacao", ignore = true) // Ignora o dataCadastro durante a atualização
     void updateEntityFromDTO(ListaRequestDTO requestDTO, @MappingTarget Lista lista);
}
