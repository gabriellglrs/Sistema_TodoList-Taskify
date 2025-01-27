package com.dev.gabriellucas.taskify.services;

import com.dev.gabriellucas.taskify.DTO.ListaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.ListaRequestPatchDTO;
import com.dev.gabriellucas.taskify.DTO.ListaResponseDTO;
import com.dev.gabriellucas.taskify.DTO.TarefaResponseDTO;

import java.util.List;

public interface ListaService {
    ListaResponseDTO saveLista(ListaRequestDTO requestDTO);
    ListaResponseDTO findByIdLista(Long id);
    ListaResponseDTO updateLista(Long id, ListaRequestDTO requestDTO);
    ListaResponseDTO updateParcialLista(Long id, ListaRequestPatchDTO requestDTO);
    void archiveLista(Long id, Long userId);
    List<TarefaResponseDTO> getTarefa(Long id);
    ListaResponseDTO addUserToLista(Long id, Long userId);
    ListaResponseDTO removeUserFromLista(Long id);

}
