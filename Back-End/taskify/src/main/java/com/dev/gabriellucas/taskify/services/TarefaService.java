package com.dev.gabriellucas.taskify.services;

import com.dev.gabriellucas.taskify.DTO.*;

import java.util.List;

public interface TarefaService {

    TarefaResponseDTO saveTarefa(TarefaRequestDTO request, Long idLista);
    TarefaResponseDTO findTarefaById(Long id);
    TarefaResponseDTO updateTarefa(TarefaRequestDTO request, Long id);
    TarefaResponseDTO updateTarefaParcial(TarefaRequestPatchDTO request, Long id);
    TarefaResponseDTO completeTarefa(Long id);
    List<ComentarioResponseDTO> findAllCommentsByTarefaId(Long id);
    List<AnexoResponseDTO> findAllAnexosByTarefaId(Long id);
    List<HistoricoResponseDTO> findAllHistoricosByTarefaId(Long id);
    void addEtiquetaToTarefa(Long idTarefa, List<EtiquetaInsertRequestDTO> request);
    void removeEtiquetaFromTarefa(Long idTarefa, Long idEtiqueta);
    TarefaResponseDTO addCategoriaToTarefa(Long tarefaId, Long categoriaId);
    TarefaResponseDTO removeCategoriaFromTarefa(Long tarefaId, Long categoriaId);
}
