package com.dev.gabriellucas.taskify.services.impl;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.entities.Usuario;
import com.dev.gabriellucas.taskify.exceptions.DatabaseException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.mappers.ListaMapper;
import com.dev.gabriellucas.taskify.mappers.NotificacaoMapper;
import com.dev.gabriellucas.taskify.mappers.UsuarioMapper;
import com.dev.gabriellucas.taskify.repositories.ListaRepository;
import com.dev.gabriellucas.taskify.repositories.NotificacaoRepository;
import com.dev.gabriellucas.taskify.repositories.UsuarioRepository;
import com.dev.gabriellucas.taskify.services.UsuarioService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

     private final UsuarioRepository usuarioRepository;
     private final ListaRepository listaRepository;
     private final NotificacaoRepository notificacaoRepository;
     private final UsuarioMapper usuarioMapper;
     private final ListaMapper listaMapper;
     private final NotificacaoMapper notificacaoMapper;
     private final PasswordEncoder password;

     public UsuarioServiceImpl(UsuarioRepository usuarioRepository, ListaRepository listaRepository, NotificacaoRepository notificacaoRepository, UsuarioMapper usuarioMapper, ListaMapper listaMapper, NotificacaoMapper notificacaoMapper, PasswordEncoder password) {
          this.usuarioRepository = usuarioRepository;
          this.listaRepository = listaRepository;
          this.notificacaoRepository = notificacaoRepository;
          this.usuarioMapper = usuarioMapper;
          this.listaMapper = listaMapper;
          this.notificacaoMapper = notificacaoMapper;
          this.password = password;
     }

     @Override
     @Transactional
     public UsuarioResponseDTO saveUsuario(UsuarioRequestDTO requestDTO) {
          Usuario usuario = usuarioMapper.toEntity(requestDTO);
          usuario.setSenha(password.encode(usuario.getSenha())); // criptografa a senha
          usuario.setDataCadastro(LocalDateTime.now()); // data de cadastro
          Usuario savedUsuario = usuarioRepository.save(usuario);
          return usuarioMapper.toDTO(savedUsuario);
     }

     @Override
     @Transactional(readOnly = true)
     @Cacheable(value = "usuarios", key = "#id")
     public UsuarioResponseDTO findByIdUsuario(Long id) {
          Usuario usuario = usuarioRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + id));
          return usuarioMapper.toDTO(usuario);
     }

     @Override
     @Transactional
     @CachePut(value = "usuarios", key = "#result.id")
     public UsuarioResponseDTO updateUsuario(Long id, UsuarioRequestDTO requestDTO) {
          Usuario usuario = usuarioRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + id));
          usuarioMapper.updateEntityFromDTO(requestDTO, usuario);
          usuario.setSenha(password.encode(usuario.getSenha())); // criptografa a senha
          Usuario updatedUsuario = usuarioRepository.save(usuario);
          return usuarioMapper.toDTO(updatedUsuario);
     }

     @Override
     @Transactional
     @CachePut(value = "usuarios", key = "#result.id")
     public UsuarioResponseDTO updateParcialUsuario(Long id, UsuarioRequestPatchDTO requestDTO) {
          Usuario usuario = usuarioRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + id));

          if (requestDTO.getNome() != null) usuario.setNome(requestDTO.getNome());
          if (requestDTO.getEmail() != null) usuario.setEmail(requestDTO.getEmail());
          if (requestDTO.getSenha() != null) usuario.setSenha(password.encode(requestDTO.getSenha())); // criptografa a senha
          if (requestDTO.getDataCadastro() != null) usuario.setDataCadastro(requestDTO.getDataCadastro());
          usuario = usuarioRepository.save(usuario);
          return usuarioMapper.toDTO(usuario);
     }

     @Override
     @Transactional
     @CacheEvict(value = "usuarios", key = "#id")
     public void deleteByIdUsuario(Long id) {
          if (!usuarioRepository.existsById(id)) {
               throw new ResourceNotFoundException("Usuário não encontrado, id: " + id);
          }
          try {
               usuarioRepository.deleteById(id);
          } catch (DataIntegrityViolationException e) {
               throw new DatabaseException("Violação de integridade");
          }
     }

     @Override
     @Transactional(readOnly = true)
     public List<ListaResponseDTO> findByUsuarioListas(Long id) {
          Usuario usuario = usuarioRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + id));
          return listaMapper.toDTOListLista(listaRepository.findByUsuario(usuario));
     }

     @Override
     @Transactional(readOnly = true)
     public List<NotificacaoResponseDTO> findByUsuarioNotificacoes(Long id) {
          Usuario usuario = usuarioRepository.findById(id)
                  .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado, id: " + id));
          return notificacaoMapper.toDTOListNotificacao(notificacaoRepository.findByUsuario(usuario));
     }

     @Transactional
     public Usuario findByEmail(String email) {
          return usuarioRepository.findByEmailWithRoles(email)
                  .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado, email: " + email));
     }
}
