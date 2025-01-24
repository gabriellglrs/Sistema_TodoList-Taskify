package com.dev.gabriellucas.taskify.services.impl;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.exceptions.DatabaseException;
import com.dev.gabriellucas.taskify.mappers.ListaMapper;
import com.dev.gabriellucas.taskify.mappers.NotificacaoMapper;
import com.dev.gabriellucas.taskify.mappers.UsuarioMapper;
import com.dev.gabriellucas.taskify.entities.Usuario;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.repositories.ListaRepository;
import com.dev.gabriellucas.taskify.repositories.NotificacaoRepository;
import com.dev.gabriellucas.taskify.repositories.UsuarioRepository;
import com.dev.gabriellucas.taskify.services.UsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

     private final UsuarioRepository usuarioRepository;
     private final ListaRepository listaRepository;
     private final NotificacaoRepository notificacaoRepository;
     private final UsuarioMapper usuarioMapper;
     private final ListaMapper listaMapper;
     private final NotificacaoMapper notificacaoMapper;

     public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ListaRepository listaRepository, NotificacaoRepository notificacaoRepository, UsuarioMapper usuarioMapper, ListaMapper listaMapper, NotificacaoMapper notificacaoMapper) {
          this.usuarioRepository = usuarioRepository;
          this.listaRepository = listaRepository;
          this.notificacaoRepository = notificacaoRepository;
          this.usuarioMapper = usuarioMapper;
          this.listaMapper = listaMapper;
          this.notificacaoMapper = notificacaoMapper;
     }

     @Override
     public UsuarioResponseDTO save(UsuarioRequestDTO requestDTO) {
          Usuario usuario = usuarioMapper.toEntity(requestDTO);
          Usuario sevedUsuario = usuarioRepository.save(usuario);
          return usuarioMapper.toDTO(sevedUsuario);

     }

     @Override
     public UsuarioResponseDTO findById(Long id) {
          Usuario usuario = usuarioRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + id));
          return usuarioMapper.toDTO(usuario);
     }

     @Override
     public UsuarioResponseDTO update(Long id, UsuarioRequestDTO requestDTO) {
          Usuario usuario = usuarioRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + id));
          usuarioMapper.updateEntityFromDTO(requestDTO, usuario);
          Usuario updateUsuario = usuarioRepository.save(usuario);
          return usuarioMapper.toDTO(updateUsuario);
     }

     @Override
     public UsuarioResponseDTO updateParcial(Long id, UsuarioRequestPatchDTO requestDTO) {
          Usuario usuario = usuarioRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + id));

          if (requestDTO.getNome() != null) usuario.setNome(requestDTO.getNome());
          if (requestDTO.getEmail() != null) usuario.setEmail(requestDTO.getEmail());
          if (requestDTO.getSenha() != null) usuario.setSenha(requestDTO.getSenha());
          if (requestDTO.getDataCadastro() != null) usuario.setDataCadastro(requestDTO.getDataCadastro());

          usuario = usuarioRepository.save(usuario);

          return usuarioMapper.toDTO(usuario);
     }

     @Override
     public void deleteById(Long id) {
          if (!usuarioRepository.existsById(id)) {
               throw new ResourceNotFoundException("Usuário não encontrado, id: " + id);
          }
          try {
               // Exclui o produto do banco de dados
               usuarioRepository.deleteById(id);
          } catch (DataIntegrityViolationException e) {
               // Lança uma exceção se houver violação de integridade (ex: FK)
               throw new DatabaseException("Violação de integridade");
          }
     }

     @Override
     public List<ListaResponseDTO> findByUsuarioListas(Long id) {
          Usuario usuario = usuarioRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + id));
          return listaMapper.toDTOListLista(listaRepository.findByUsuario(usuario));
     }

     @Override
     public List<NotificacaoResponseDTO> findByUsuarioNotificacoes(Long id) {
          Usuario usuario = usuarioRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + id));
          return notificacaoMapper.toDTOListNotificacao(notificacaoRepository.findByUsuario(usuario));
     }
}
