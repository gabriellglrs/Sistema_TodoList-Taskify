package com.dev.gabriellucas.taskify.services;

import com.dev.gabriellucas.taskify.DTO.*;

import java.util.List;

public interface UsuarioService {

     UsuarioResponseDTO save(UsuarioRequestDTO requestDTO);
     UsuarioResponseDTO findById(Long id);
     UsuarioResponseDTO update(Long id, UsuarioRequestDTO requestDTO);
     UsuarioResponseDTO updateParcial(Long id, UsuarioRequestPatchDTO requestDTO);
     void deleteById(Long id);
     List<ListaResponseDTO> findByUsuarioListas(Long id);
     List<NotificacaoResponseDTO> findByUsuarioNotificacoes(Long id);
}
