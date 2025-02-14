package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.UsuarioRequestDTO;
import com.dev.gabriellucas.taskify.DTO.UsuarioResponseDTO;
import com.dev.gabriellucas.taskify.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioResponseDTO toDTO(Usuario usuario);

    Usuario toEntity(UsuarioRequestDTO requestDTO);

    List<UsuarioResponseDTO> toDTOListUsuario(List<Usuario> usuarioList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    void updateEntityFromDTO(UsuarioRequestDTO requestDTO, @MappingTarget Usuario usuario);

}

