package com.dev.gabriellucas.taskify.services.impl;

import com.dev.gabriellucas.taskify.DTO.ListaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.ListaRequestPatchDTO;
import com.dev.gabriellucas.taskify.DTO.ListaResponseDTO;
import com.dev.gabriellucas.taskify.DTO.TarefaResponseDTO;
import com.dev.gabriellucas.taskify.entities.Lista;
import com.dev.gabriellucas.taskify.entities.Usuario;
import com.dev.gabriellucas.taskify.enums.StatusLista;
import com.dev.gabriellucas.taskify.exceptions.BusinessException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.mappers.ListaMapper;
import com.dev.gabriellucas.taskify.mappers.TarefaMapper;
import com.dev.gabriellucas.taskify.repositories.ListaRepository;
import com.dev.gabriellucas.taskify.repositories.TarefaRepository;
import com.dev.gabriellucas.taskify.repositories.UsuarioRepository;
import com.dev.gabriellucas.taskify.services.ListaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListaServiceImpl implements ListaService {


     private final ListaRepository listaRepository;
     private final ListaMapper listaMapper;
     private final TarefaMapper tarefaMapper;
     private final TarefaRepository tarefaRepository;
     private final UsuarioRepository usuarioRepository;

     public ListaServiceImpl(ListaRepository listaRepository, ListaMapper listaMapper, TarefaMapper tarefaMapper, TarefaRepository tarefaRepository, UsuarioRepository usuarioRepository) {
          this.listaRepository = listaRepository;
          this.listaMapper = listaMapper;
          this.tarefaMapper = tarefaMapper;
          this.tarefaRepository = tarefaRepository;
          this.usuarioRepository = usuarioRepository;
     }

     @Override
     @Transactional
     public ListaResponseDTO saveLista(ListaRequestDTO requestDTO) {
          Lista lista = listaMapper.toEntity(requestDTO);
          return listaMapper.toDTO(listaRepository.save(lista));
     }

     @Override
     @Transactional(readOnly = true)
     public ListaResponseDTO findByIdLista(Long id) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          return listaMapper.toDTO(lista);
     }

     @Override
     @Transactional
     public ListaResponseDTO updateLista(Long id, ListaRequestDTO requestDTO) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          validateListaUpdate(lista);
          listaMapper.updateEntityFromDTO(requestDTO, lista);
          return listaMapper.toDTO(listaRepository.save(lista));
     }

     @Override
     @Transactional
     public ListaResponseDTO updateParcialLista(Long id, ListaRequestPatchDTO requestDTO) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          validateListaUpdate(lista);
          if (requestDTO.getTitulo() != null) lista.setTitulo(requestDTO.getTitulo());
          if (requestDTO.getDescricao() != null) lista.setDescricao(requestDTO.getDescricao());
          if (requestDTO.getDataCriacao() != null) lista.setDataCriacao(requestDTO.getDataCriacao());
          if (requestDTO.getStatus() != null) lista.setStatus(requestDTO.getStatus());
          return listaMapper.toDTO(listaRepository.save(lista));
     }

     @Override
     @Transactional
     public void archiveLista(Long id, Long userId) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          if (!lista.getUsuario().getId().equals(userId)) {
               throw new BusinessException("Apenas o proprietario pode arquivar a lista");
          }
          lista.setStatus(StatusLista.ARQUIVADA);
          listaRepository.save(lista);
     }

     @Override
     @Transactional(readOnly = true)
     public List<TarefaResponseDTO> getTarefa(Long id) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          return tarefaMapper.toListDto(tarefaRepository.findByLista(lista));
     }

     @Override
     @Transactional
     public ListaResponseDTO addUserToLista(Long id, Long userId) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          Usuario usuario = usuarioRepository.findById(userId)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + userId));
          lista.setUsuario(usuario);
          return listaMapper.toDTO(listaRepository.save(lista));
     }

     @Override
     @Transactional
     public ListaResponseDTO removeUserFromLista(Long id) {
          Lista lista = listaRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada, id:" + id));
          lista.setUsuario(null);
          return listaMapper.toDTO(listaRepository.save(lista));
     }

     private void validateListaUpdate(Lista lista) {
          if (lista.getStatus() == StatusLista.ARQUIVADA) {
               throw new BusinessException("Alterações não são permitidas em listas arquivadas.");
          }
     }
}
