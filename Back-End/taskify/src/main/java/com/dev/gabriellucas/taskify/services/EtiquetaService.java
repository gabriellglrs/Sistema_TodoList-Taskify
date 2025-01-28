package com.dev.gabriellucas.taskify.services;

import com.dev.gabriellucas.taskify.DTO.EtiquetaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.EtiquetaRequestPatchDTO;
import com.dev.gabriellucas.taskify.DTO.EtiquetaResponseDTO;

public interface EtiquetaService {

    EtiquetaResponseDTO saveEtiqueta(EtiquetaRequestDTO request);
    EtiquetaResponseDTO findEtiquetaById(Long id);
    EtiquetaResponseDTO updateEtiqueta(Long id, EtiquetaRequestDTO request);
    void deleteEtiqueta(Long id);
    EtiquetaResponseDTO updateEtiquetaParcial(Long id, EtiquetaRequestPatchDTO request);

}
