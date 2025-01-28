package com.dev.gabriellucas.taskify.services.impl;

import com.dev.gabriellucas.taskify.DTO.CategoriaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.CategoriaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Categoria;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.mappers.CategoriaMapper;
import com.dev.gabriellucas.taskify.repositories.CategoriaRepository;
import com.dev.gabriellucas.taskify.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServiceImpl implements CategoriaService {
     private final CategoriaRepository repository;
     private final CategoriaMapper categoriaMapper;

     @Autowired
     public CategoriaServiceImpl(CategoriaRepository repository, CategoriaMapper categoriaMapper) {
          this.repository = repository;
          this.categoriaMapper = categoriaMapper;
     }

     @Override
     public CategoriaResponseDTO saveCategoria(CategoriaRequestDTO requestDTO) {
          Categoria categoria = categoriaMapper.toEntity(requestDTO);
          return categoriaMapper.toDTO(repository.save(categoria));
     }

     @Override
     public CategoriaResponseDTO findByIdCategoria(Long id) {
          Categoria categoria = repository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrado, id: " + id));
          return categoriaMapper.toDTO(categoria);
     }

     @Override
     public CategoriaResponseDTO updateCategoria(Long id, CategoriaRequestDTO requestDTO) {
          return null;
     }

     @Override
     public CategoriaResponseDTO updateParcialCategoria(Long id, CategoriaRequestDTO requestDTO) {
          return null;
     }

     @Override
     public void deleteByIdCategoria(Long id) {

     }

     @Override
     public CategoriaResponseDTO addUserToCategoria(Long id, Long userId) {
          return null;
     }

     @Override
     public CategoriaResponseDTO removeUserFromCategoria(Long id) {
          return null;
     }
}
