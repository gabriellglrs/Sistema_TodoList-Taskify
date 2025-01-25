package com.dev.gabriellucas.taskify.services;

import com.dev.gabriellucas.taskify.DTO.ListaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.ListaRequestPatchDTO;
import com.dev.gabriellucas.taskify.DTO.ListaResponseDTO;

public interface ListaService {
    ListaResponseDTO saveLista(ListaRequestDTO requestDTO);
    ListaResponseDTO findByIdLista(Long id);
    ListaResponseDTO updateLista(Long id, ListaRequestDTO requestDTO);
    ListaResponseDTO updateParcialLista(Long id, ListaRequestPatchDTO requestDTO);

}
