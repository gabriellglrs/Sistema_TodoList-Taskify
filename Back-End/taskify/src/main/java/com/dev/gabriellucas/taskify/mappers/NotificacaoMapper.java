package com.dev.gabriellucas.taskify.mappers;

import com.dev.gabriellucas.taskify.DTO.NotificacaoResponseDTO;
import com.dev.gabriellucas.taskify.entities.Notificacao;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificacaoMapper {
     NotificacaoResponseDTO map(Notificacao notificacao);
     List<NotificacaoResponseDTO> toDTOListNotificacao(List<Notificacao> listaNotificacaos);
}
