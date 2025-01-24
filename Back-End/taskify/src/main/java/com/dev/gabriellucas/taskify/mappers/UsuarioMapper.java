package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.ListaResponseDTO;
import com.dev.gabriellucas.taskify.DTO.NotificacaoResponseDTO;
import com.dev.gabriellucas.taskify.DTO.UsuarioRequestDTO;
import com.dev.gabriellucas.taskify.DTO.UsuarioResponseDTO;
import com.dev.gabriellucas.taskify.entities.Lista;
import com.dev.gabriellucas.taskify.entities.Notificacao;
import com.dev.gabriellucas.taskify.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    // Converte UserEntity -> UserDTO
    UsuarioResponseDTO toDTO(Usuario usuario);

    // Converte UserDTO -> UserEntity
    Usuario toEntity(UsuarioRequestDTO requestDTO);

    // Converte uma lista de entidades em uma lista de DTOs
    List<UsuarioResponseDTO> toDTOListUsuario(List<Usuario> usuarioList);

    // Atualiza a entidade existente com os valores do DTO
    @Mapping(target = "id", ignore = true) // Ignora o ID durante a atualização
    @Mapping(target = "dataCadastro", ignore = true) // Ignora o dataCadastro durante a atualização
    void updateEntityFromDTO(UsuarioRequestDTO requestDTO, @MappingTarget Usuario usuario);


}
