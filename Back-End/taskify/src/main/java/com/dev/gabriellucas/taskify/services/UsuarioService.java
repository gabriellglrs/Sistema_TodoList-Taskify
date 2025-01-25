package com.dev.gabriellucas.taskify.services;

import com.dev.gabriellucas.taskify.DTO.*;

import java.util.List;

public interface UsuarioService {

     UsuarioResponseDTO saveUsuario(UsuarioRequestDTO requestDTO);
     UsuarioResponseDTO findByIdUsuario(Long id);
     UsuarioResponseDTO updateUsuario(Long id, UsuarioRequestDTO requestDTO);
     UsuarioResponseDTO updateParcialUsuario(Long id, UsuarioRequestPatchDTO requestDTO);
     void deleteByIdUsuario(Long id);
     List<ListaResponseDTO> findByUsuarioListas(Long id);
     List<NotificacaoResponseDTO> findByUsuarioNotificacoes(Long id);
}
