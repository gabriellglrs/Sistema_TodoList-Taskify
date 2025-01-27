package com.dev.gabriellucas.taskify.services.impl;

import com.dev.gabriellucas.taskify.DTO.ListaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.ListaRequestPatchDTO;
import com.dev.gabriellucas.taskify.DTO.ListaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Lista;
import com.dev.gabriellucas.taskify.enums.StatusLista;
import com.dev.gabriellucas.taskify.exceptions.BusinessException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.mappers.ListaMapper;
import com.dev.gabriellucas.taskify.repositories.ListaRepository;
import com.dev.gabriellucas.taskify.services.ListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListaServiceImpl implements ListaService {

     @Autowired
     ListaRepository listaRepository;

     @Autowired
     ListaMapper listaMapper;

     @Override
     public ListaResponseDTO saveLista(ListaRequestDTO requestDTO) {
          Lista lista = listaMapper.toEntity(requestDTO);
          return listaMapper.toDTO(listaRepository.save(lista));
     }

     @Override
     public ListaResponseDTO findByIdLista(Long id) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          return listaMapper.toDTO(lista);
     }

     @Override
     public ListaResponseDTO updateLista(Long id, ListaRequestDTO requestDTO) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          listaMapper.updateEntityFromDTO(requestDTO, lista);
          return listaMapper.toDTO(listaRepository.save(lista));
     }

     @Override
     public ListaResponseDTO updateParcialLista(Long id, ListaRequestPatchDTO requestDTO) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          if (requestDTO.getTitulo() != null) lista.setTitulo(requestDTO.getTitulo());
          if (requestDTO.getDescricao() != null) lista.setDescricao(requestDTO.getDescricao());
          if (requestDTO.getDataCriacao() != null) lista.setDataCriacao(requestDTO.getDataCriacao());
          if (requestDTO.getStatus() != null) lista.setStatus(requestDTO.getStatus());
          return listaMapper.toDTO(listaRepository.save(lista));
     }

     @Override
     public void archiveLista(Long id, Long userId) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          if (!lista.getUsuario().getId().equals(userId)) {
               throw new BusinessException("Apenas o proprietario pode arquivar a lista");
          }
          lista.setStatus(StatusLista.ARQUIVADA);
          listaRepository.save(lista);
     }
}
