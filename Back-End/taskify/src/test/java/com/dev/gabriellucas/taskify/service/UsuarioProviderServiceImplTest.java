package com.dev.gabriellucas.taskify.service;

import com.dev.gabriellucas.taskify.DTO.TarefaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.UsuarioRequestDTO;
import com.dev.gabriellucas.taskify.DTO.UsuarioRequestPatchDTO;
import com.dev.gabriellucas.taskify.DTO.UsuarioResponseDTO;
import com.dev.gabriellucas.taskify.entities.Tarefa;
import com.dev.gabriellucas.taskify.entities.Usuario;
import com.dev.gabriellucas.taskify.mappers.ListaMapper;
import com.dev.gabriellucas.taskify.mappers.NotificacaoMapper;
import com.dev.gabriellucas.taskify.mappers.UsuarioMapper;
import com.dev.gabriellucas.taskify.provider.UsuarioProvider;
import com.dev.gabriellucas.taskify.provider.UsuarioRequestDTOProvider;
import com.dev.gabriellucas.taskify.provider.UsuarioRequestPatchDTOProvider;
import com.dev.gabriellucas.taskify.provider.UsuarioResponseDTOProvider;
import com.dev.gabriellucas.taskify.repositories.ListaRepository;
import com.dev.gabriellucas.taskify.repositories.NotificacaoRepository;
import com.dev.gabriellucas.taskify.repositories.UsuarioRepository;
import com.dev.gabriellucas.taskify.services.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UsuarioProviderServiceImplTest {

     @InjectMocks
     private UsuarioServiceImpl usuarioService;

     @Mock
     private  UsuarioRepository usuarioRepository;

     @Mock
     private  ListaRepository listaRepository;

     @Mock
     private  NotificacaoRepository notificacaoRepository;

     @Mock
     private  UsuarioMapper usuarioMapper;

     @Mock
     private  ListaMapper listaMapper;

     @Mock
     private  NotificacaoMapper notificacaoMapper;


     private UsuarioProvider usuarioProvider;
     private UsuarioRequestDTOProvider requestDTOProvider;
     private UsuarioResponseDTOProvider responseDTOProvider;
     private UsuarioRequestPatchDTOProvider requestPatchDTOProvider;

     @BeforeEach
     void setUp() {
          MockitoAnnotations.openMocks(this);
          usuarioProvider = new UsuarioProvider();
          requestDTOProvider = new UsuarioRequestDTOProvider();
          responseDTOProvider = new UsuarioResponseDTOProvider();
          requestPatchDTOProvider = new UsuarioRequestPatchDTOProvider();
          usuarioService = new UsuarioServiceImpl(usuarioRepository, listaRepository, notificacaoRepository, usuarioMapper, listaMapper, notificacaoMapper, null);
     }

     @DisplayName("Teste de criação de usuário")
     @Test
     void saveUsuario() {
          Usuario usuario = usuarioProvider.create();
          UsuarioRequestDTO requestDTO = requestDTOProvider.create();
          UsuarioResponseDTO responseDTO = responseDTOProvider.create();
          UsuarioRequestPatchDTO requestPatchDTO = requestPatchDTOProvider.create();

          Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
          Mockito.when(usuarioMapper.toEntity(Mockito.any(UsuarioRequestDTO.class))).thenReturn(usuario);
          Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
          Mockito.when(usuarioMapper.toDTO(Mockito.any(Usuario.class))).thenReturn(responseDTO);

          UsuarioResponseDTO result = usuarioService.saveUsuario(requestDTO);

          Assertions.assertNotNull(result);
          Assertions.assertEquals(UsuarioResponseDTO.class, responseDTO.getClass());
     }

     @Test
     void findByIdUsuario() {
     }

     @Test
     void updateUsuario() {
     }

     @Test
     void updateParcialUsuario() {
     }

     @Test
     void deleteByIdUsuario() {
     }

     @Test
     void findByUsuarioListas() {
     }

     @Test
     void findByUsuarioNotificacoes() {
     }

}