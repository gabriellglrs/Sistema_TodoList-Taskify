package com.dev.gabriellucas.taskify.services;

import com.dev.gabriellucas.taskify.DTO.*;

public interface CategoriaService {
     CategoriaResponseDTO saveCategoria(CategoriaRequestDTO requestDTO);
     CategoriaResponseDTO findByIdCategoria(Long id);
     CategoriaResponseDTO updateCategoria(Long id, CategoriaRequestDTO requestDTO);
     CategoriaResponseDTO updateParcialCategoria(Long id, CategoriaRequestPatchDTO requestDTO);
     void deleteByIdCategoria(Long id);
}
