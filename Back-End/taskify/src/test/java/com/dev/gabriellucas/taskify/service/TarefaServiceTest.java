package com.dev.gabriellucas.taskify.service;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.entities.*;
import com.dev.gabriellucas.taskify.enums.StatusTarefa;
import com.dev.gabriellucas.taskify.exceptions.BusinessException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.mappers.*;
import com.dev.gabriellucas.taskify.provider.*;
import com.dev.gabriellucas.taskify.repositories.*;
import com.dev.gabriellucas.taskify.services.impl.TarefaServiceImpl;
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

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    public static final Long ID = 1L;

    @InjectMocks
    private TarefaServiceImpl service;

    @Mock
    private TarefaRepository repository;

    @Mock
    private TarefaMapper tarefaMapper;

    @Mock
    private ListaRepository listaRepository;

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private ComentarioMapper comentarioMapper;

    @Mock
    private AnexoRepository anexoRepository;

    @Mock
    private AnexoMapper anexoMapper;

    @Mock
    private HistoricoRepository historicoRepository;

    @Mock
    private HistoricoMapper historicoMapper;

    @Mock
    private EtiquetaRepository etiquetaRepository;

    @Mock
    private EtiquetaMapper etiquetaMapper;

    @Mock
    private CategoriaRepository categoriaRepository;

    private TarefaProvider tarefaProvider;
    private ListaProvider listaProvider;
    private TarefaRequestDTOProvider tarefaRequestDTOProvider;
    private TarefaResponseDTOProvider tarefaResponseDTOProvider;
    private TarefaRequestPatchDTOProvider tarefaRequestPatchDTOProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tarefaProvider = new TarefaProvider();
        listaProvider = new ListaProvider();
        tarefaResponseDTOProvider = new TarefaResponseDTOProvider();
        tarefaRequestDTOProvider = new TarefaRequestDTOProvider();
        tarefaRequestPatchDTOProvider = new TarefaRequestPatchDTOProvider();
        service = new TarefaServiceImpl(repository, tarefaMapper, listaRepository, comentarioRepository, comentarioMapper,
                anexoRepository, anexoMapper, historicoRepository, historicoMapper, etiquetaRepository, categoriaRepository, etiquetaMapper);
    }

    @DisplayName("Deve criar com sucesso uma Tarefa. ")
    @Test
    public void shouldCreatedTarefaWithSuccess() {
        Tarefa tarefa = tarefaProvider.create();
        Lista lista = listaProvider.create();

        TarefaRequestDTO tarefaRequestDTO = tarefaRequestDTOProvider.create();
        TarefaResponseDTO tarefaResponseDTO = tarefaResponseDTOProvider.create();

        Mockito.when(listaRepository.findById(ID)).thenReturn(Optional.of(lista));
        Mockito.when(tarefaMapper.toEntity(Mockito.any(TarefaRequestDTO.class))).thenReturn(tarefa);
        Mockito.when(repository.save(Mockito.any(Tarefa.class))).thenReturn(tarefa);
        Mockito.when(tarefaMapper.toDto(Mockito.any(Tarefa.class))).thenReturn(tarefaResponseDTO);

        TarefaResponseDTO responseDTO = service.saveTarefa(tarefaRequestDTO, ID);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(TarefaResponseDTO.class, responseDTO.getClass());

        Mockito.verify(listaRepository, Mockito.times(1)).findById(ID);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Tarefa.class));
    }

    @DisplayName("Metodo saveTarefa deve retornar lista não encontrada.")
    @Test
    public void shouldSaveTarefaReturnListaNotFound() {
        TarefaRequestDTO tarefaRequestDTO = tarefaRequestDTOProvider.create();

        Mockito.when(listaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.saveTarefa(tarefaRequestDTO, ID);
        });

        Assertions.assertEquals("Lista não encontrada, id:" + ID, exception.getMessage());
    }

    @DisplayName("Find By Id Deve retornar uma Tarefa com sucesso.")
    @Test
    public void shouldFindTarefaByIdAndReturnTarefaResponseDTO() {
        Tarefa tarefa = tarefaProvider.create();
        TarefaResponseDTO tarefaResponseDTO = tarefaResponseDTOProvider.create();

        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(tarefa));
        Mockito.when(tarefaMapper.toDto(Mockito.any(Tarefa.class))).thenReturn(tarefaResponseDTO);

        TarefaResponseDTO responseDTO = service.findTarefaById(ID);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(TarefaResponseDTO.class, responseDTO.getClass());

        Mockito.verify(repository, Mockito.times(1)).findById(ID);
    }

    @DisplayName("Find By Id deve retornar Tarefa não encontrada. ")
    @Test
    public void shouldReturnTarefaNotFoundInFindById() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findTarefaById(ID);
        });

        Assertions.assertEquals("Tarefa não encontrada, id:" + ID, exception.getMessage());
    }

    @DisplayName("Deve atualizar uma Tarefa com sucesso. ")
    @Test
    public void shouldReturnNewTarefaWhenUpdateWithSuccess() {
        Tarefa tarefa = tarefaProvider.create();
        TarefaResponseDTO tarefaResponseDTO = tarefaResponseDTOProvider.create();
        TarefaRequestDTO tarefaRequestDTO = tarefaRequestDTOProvider.create();

        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(tarefa));
        Mockito.doNothing().when(tarefaMapper).updateEntityFromDTO(tarefaRequestDTO, tarefa);
        Mockito.when(repository.save(Mockito.any(Tarefa.class))).thenReturn(tarefa);
        Mockito.when(tarefaMapper.toDto(Mockito.any(Tarefa.class))).thenReturn(tarefaResponseDTO);

        TarefaResponseDTO responseDTO = service.updateTarefa(tarefaRequestDTO, ID);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(TarefaResponseDTO.class, responseDTO.getClass());

        Mockito.verify(repository, Mockito.times(1)).findById(ID);
        Mockito.verify(tarefaMapper, Mockito.times(1)).updateEntityFromDTO(tarefaRequestDTO, tarefa);
        Mockito.verify(repository, Mockito.times(1)).save(tarefa);
        Mockito.verify(tarefaMapper, Mockito.times(1)).toDto(tarefa);
    }

    @DisplayName("Deve retornar tarefa não encontrada ao atualizar. ")
    @Test
    public void shouldReturnTarefaNotFoundWhenTryUpdate() {
        TarefaRequestDTO tarefaRequestDTO = tarefaRequestDTOProvider.create();

        Mockito.when(repository.findById(ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.updateTarefa(tarefaRequestDTO, ID);
        });

        Assertions.assertEquals("Tarefa não encontrada, id:" + ID, exception.getMessage());
    }

    @DisplayName("Deve retornar tarefa parcialmente atualizada com sucesso. ")
    @Test
    public void shouldReturnTarefaUpdatedParcialComSucesso() {
        Tarefa tarefa = tarefaProvider.create();
        TarefaResponseDTO tarefaResponseDTO = tarefaResponseDTOProvider.create();
        TarefaRequestPatchDTO tarefaRequestPatchDTO = tarefaRequestPatchDTOProvider.create();

        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(tarefa));
        Mockito.when(repository.save(Mockito.any(Tarefa.class))).thenReturn(tarefa);
        Mockito.when(tarefaMapper.toDto(Mockito.any(Tarefa.class))).thenReturn(tarefaResponseDTO);

        TarefaResponseDTO responseDTO = service.updateTarefaParcial(tarefaRequestPatchDTO, ID);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(TarefaResponseDTO.class, responseDTO.getClass());

        Mockito.verify(repository, Mockito.times(1)).findById(ID);
        Mockito.verify(repository, Mockito.times(1)).save(tarefa);
        Mockito.verify(tarefaMapper, Mockito.times(1)).toDto(tarefa);
    }

    @DisplayName("Deve retornar tarefa não encontrada ao atualizar parcialmente. ")
    @Test
    public void shoulReturnTarefaNotFoundWhenUpdateingParcial() {
        TarefaRequestPatchDTO tarefaRequestPatchDTO = tarefaRequestPatchDTOProvider.create();

        Mockito.when(repository.findById(ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.updateTarefaParcial(tarefaRequestPatchDTO, ID);
        });

        Assertions.assertEquals("Tarefa não encontrada, id:" + ID, exception.getMessage());
    }

    @DisplayName("Deve retornar tarefa concluída com sucesso.")
    @Test
    public void shouldReturnCompletedTaskWithSuccess(){
        Tarefa tarefa = tarefaProvider.create();
        TarefaResponseDTO tarefaResponseDTO = tarefaResponseDTOProvider.create();

        tarefa.setStatus(StatusTarefa.CONCLUIDA);

        tarefaResponseDTO.setStatus(StatusTarefa.CONCLUIDA);

        Mockito.when(repository.findById(ID)).thenReturn(Optional.of(tarefa));
        Mockito.when(repository.save(Mockito.any(Tarefa.class))).thenReturn(tarefa);
        Mockito.when(tarefaMapper.toDto(Mockito.any(Tarefa.class))).thenReturn(tarefaResponseDTO);

        TarefaResponseDTO responseDTO = service.completeTarefa(ID);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(TarefaResponseDTO.class, responseDTO.getClass());
        Assertions.assertEquals(responseDTO.getStatus(), StatusTarefa.CONCLUIDA);

        Mockito.verify(repository, Mockito.times(1)).findById(ID);
        Mockito.verify(repository, Mockito.times(1)).save(tarefa);
        Mockito.verify(tarefaMapper, Mockito.times(1)).toDto(tarefa);
    }

    @DisplayName("Deve retornar tarefa não ao marcar tarefa como concluída.")
    @Test
    public void shouldReturnErrorWhenTryingToMarkTaskAsCompletedAndTaskNotFound() {
        Mockito.when(repository.findById(ID)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.completeTarefa(ID);
        });

        Assertions.assertEquals("Tarefa não encontrada, id:" + ID, exception.getMessage());
    }

    @DisplayName("Deve retornar todos os comentários de uma Tarefa com sucesso.")
    @Test
    public void shouldReturnAllCommentsByTarefaId() {
        Long tarefaId = 1L;
        Comentario comentario1 = new Comentario();
        comentario1.setId(1L);
        comentario1.setTexto("Primeiro comentário");

        Comentario comentario2 = new Comentario();
        comentario2.setId(2L);
        comentario2.setTexto("Segundo comentário");

        List<Comentario> comentarios = List.of(comentario1, comentario2);

        ComentarioResponseDTO responseDTO1 = new ComentarioResponseDTO();
        responseDTO1.setId(1L);
        responseDTO1.setTexto("Primeiro comentário");

        ComentarioResponseDTO responseDTO2 = new ComentarioResponseDTO();
        responseDTO2.setId(2L);
        responseDTO2.setTexto("Segundo comentário");

        Mockito.when(comentarioRepository.findAllByTarefaId(tarefaId)).thenReturn(comentarios);
        Mockito.when(comentarioMapper.toDto(comentario1)).thenReturn(responseDTO1);
        Mockito.when(comentarioMapper.toDto(comentario2)).thenReturn(responseDTO2);

        List<ComentarioResponseDTO> response = service.findAllCommentsByTarefaId(tarefaId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("Primeiro comentário", response.get(0).getTexto());
        Assertions.assertEquals("Segundo comentário", response.get(1).getTexto());

        Mockito.verify(comentarioRepository, Mockito.times(1)).findAllByTarefaId(tarefaId);
        Mockito.verify(comentarioMapper, Mockito.times(1)).toDto(comentario1);
        Mockito.verify(comentarioMapper, Mockito.times(1)).toDto(comentario2);
    }

    @DisplayName("Deve retornar todos os anexos de uma Tarefa com sucesso.")
    @Test
    public void shouldReturnAllAnexosByTarefaId() {
        Long tarefaId = 1L;

        Anexo anexo1 = new Anexo();
        anexo1.setId(1L);
        anexo1.setNome("documento.pdf");

        Anexo anexo2 = new Anexo();
        anexo2.setId(2L);
        anexo2.setNome("imagem.png");

        List<Anexo> anexos = List.of(anexo1, anexo2);

        AnexoResponseDTO dto1 = new AnexoResponseDTO();
        dto1.setId(1L);
        dto1.setNome("documento.pdf");

        AnexoResponseDTO dto2 = new AnexoResponseDTO();
        dto2.setId(2L);
        dto2.setNome("imagem.png");

        Mockito.when(anexoRepository.findAllByTarefaId(tarefaId)).thenReturn(anexos);
        Mockito.when(anexoMapper.toDto(anexo1)).thenReturn(dto1);
        Mockito.when(anexoMapper.toDto(anexo2)).thenReturn(dto2);

        List<AnexoResponseDTO> response = service.findAllAnexosByTarefaId(tarefaId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("documento.pdf", response.get(0).getNome());
        Assertions.assertEquals("imagem.png", response.get(1).getNome());

        Mockito.verify(anexoRepository, Mockito.times(1)).findAllByTarefaId(tarefaId);
        Mockito.verify(anexoMapper, Mockito.times(1)).toDto(anexo1);
        Mockito.verify(anexoMapper, Mockito.times(1)).toDto(anexo2);
    }

    @DisplayName("Deve retornar todos os históricos de uma Tarefa com sucesso.")
    @Test
    public void shouldReturnAllHistoricosByTarefaId() {
        Long tarefaId = 3L;

        Historico historico1 = new Historico();
        historico1.setId(1L);
        historico1.setDescricao("Tarefa criada");

        Historico historico2 = new Historico();
        historico2.setId(2L);
        historico2.setDescricao("Tarefa atualizada");

        List<Historico> historicos = List.of(historico1, historico2);

        HistoricoResponseDTO dto1 = new HistoricoResponseDTO();
        dto1.setId(1L);
        dto1.setDescricao("Tarefa criada");

        HistoricoResponseDTO dto2 = new HistoricoResponseDTO();
        dto2.setId(2L);
        dto2.setDescricao("Tarefa atualizada");

        Mockito.when(historicoRepository.findAllByTarefaId(tarefaId)).thenReturn(historicos);
        Mockito.when(historicoMapper.toDto(historico1)).thenReturn(dto1);
        Mockito.when(historicoMapper.toDto(historico2)).thenReturn(dto2);

        List<HistoricoResponseDTO> response = service.findAllHistoricosByTarefaId(tarefaId);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("Tarefa criada", response.get(0).getDescricao());
        Assertions.assertEquals("Tarefa atualizada", response.get(1).getDescricao());

        Mockito.verify(historicoRepository, Mockito.times(1)).findAllByTarefaId(tarefaId);
        Mockito.verify(historicoMapper, Mockito.times(1)).toDto(historico1);
        Mockito.verify(historicoMapper, Mockito.times(1)).toDto(historico2);
    }

    @DisplayName("Deve adicionar etiquetas à tarefa com sucesso.")
    @Test
    public void shouldAddEtiquetasToTarefaSuccessfully() {
        Long tarefaId = ID;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        tarefa.setEtiquetas(new HashSet<>());

        EtiquetaInsertRequestDTO requestDTO1 = new EtiquetaInsertRequestDTO();
        requestDTO1.setIdEtiqueta(1L);
        EtiquetaInsertRequestDTO requestDTO2 = new EtiquetaInsertRequestDTO();
        requestDTO2.setIdEtiqueta(2L);
        List<EtiquetaInsertRequestDTO> requestList = List.of(requestDTO1, requestDTO2);

        Etiqueta etiqueta1 = new Etiqueta();
        etiqueta1.setId(1L);
        etiqueta1.setNome("Teste 1");
        Etiqueta etiqueta2 = new Etiqueta();
        etiqueta2.setId(2L);
        etiqueta2.setNome("Teste 2");

        List<Etiqueta> etiquetas = List.of(etiqueta1, etiqueta2);

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        Mockito.when(etiquetaRepository.findAllById(List.of(1L, 2L))).thenReturn(etiquetas);

        service.addEtiquetaToTarefa(tarefaId, requestList);

        Assertions.assertEquals(2, tarefa.getEtiquetas().size());
        Assertions.assertTrue(tarefa.getEtiquetas().contains(etiqueta1));
        Assertions.assertTrue(tarefa.getEtiquetas().contains(etiqueta2));

        Mockito.verify(repository, Mockito.times(1)).save(tarefa);
    }

    @DisplayName("Deve lançar ResourceNotFoundException quando a tarefa não for encontrada.")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenTarefaNotFound() {
        Long tarefaId = ID;

        EtiquetaInsertRequestDTO requestDTO1 = new EtiquetaInsertRequestDTO();
        requestDTO1.setIdEtiqueta(ID);

        List<EtiquetaInsertRequestDTO> requestList = List.of(requestDTO1);

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> service.addEtiquetaToTarefa(tarefaId, requestList)
        );

        Assertions.assertEquals("Tarefa não encontrada, id:" + ID, exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Tarefa.class));
    }

    @DisplayName("Deve lançar BusinessException quando o limite máximo de 10 etiquetas for atingido.")
    @Test
    public void shouldThrowBusinessExceptionWhenEtiquetaLimitExceeded() {
        Long tarefaId = ID;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        Set<Etiqueta> etiquetas = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Etiqueta etiqueta = new Etiqueta();
            etiqueta.setId((long) i);
            etiqueta.setNome("Etiqueta " + i);
            etiquetas.add(etiqueta);
        }
        tarefa.setEtiquetas(etiquetas);

        EtiquetaInsertRequestDTO requestDTO = new EtiquetaInsertRequestDTO();
        requestDTO.setIdEtiqueta(11L);
        List<EtiquetaInsertRequestDTO> requestList = List.of(requestDTO);

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.of(tarefa));

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> service.addEtiquetaToTarefa(tarefaId, requestList)
        );

        Assertions.assertEquals("Limite máximo de 10 etiquetas por tarefa alcançado", exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Tarefa.class));
    }

    @DisplayName("Deve lançar ResourceNotFoundException quando uma ou mais etiquetas não forem encontradas.")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenEtiquetaNotFound() {
        Long tarefaId = ID;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        tarefa.setEtiquetas(new HashSet<>());

        EtiquetaInsertRequestDTO requestDTO = new EtiquetaInsertRequestDTO();
        requestDTO.setIdEtiqueta(1L);
        List<EtiquetaInsertRequestDTO> requestList = List.of(requestDTO);

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        Mockito.when(etiquetaRepository.findAllById(List.of(1L))).thenReturn(List.of());

        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> service.addEtiquetaToTarefa(tarefaId, requestList)
        );

        Assertions.assertEquals("Uma ou mais etiquetas não foram encontradas.", exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Tarefa.class));
    }

    @DisplayName("Deve lançar BusinessException quando uma etiqueta com o mesmo nome já estiver associada.")
    @Test
    public void shouldThrowBusinessExceptionWhenEtiquetaWithSameNameExists() {
        Long tarefaId = ID;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);

        Etiqueta existingEtiqueta = new Etiqueta();
        existingEtiqueta.setId(1L);
        existingEtiqueta.setNome("Etiqueta Existente");

        tarefa.setEtiquetas(new HashSet<>(List.of(existingEtiqueta)));

        EtiquetaInsertRequestDTO requestDTO = new EtiquetaInsertRequestDTO();
        requestDTO.setIdEtiqueta(2L);

        Etiqueta newEtiqueta = new Etiqueta();
        newEtiqueta.setId(2L);
        newEtiqueta.setNome("Etiqueta Existente");

        List<EtiquetaInsertRequestDTO> requestList = List.of(requestDTO);
        List<Etiqueta> etiquetas = List.of(newEtiqueta);

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        Mockito.when(etiquetaRepository.findAllById(List.of(2L))).thenReturn(etiquetas);

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> service.addEtiquetaToTarefa(tarefaId, requestList)
        );

        Assertions.assertEquals("Etiqueta com o nome 'Etiqueta Existente' já está associada à tarefa", exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Tarefa.class));
    }

    @DisplayName("Deve remover a etiqueta da tarefa com sucesso.")
    @Test
    public void shouldRemoveEtiquetaFromTarefaSuccessfully() {
        Long tarefaId = ID;
        Long etiquetaId = 1L;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);

        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setId(etiquetaId);
        tarefa.setEtiquetas(new HashSet<>(List.of(etiqueta)));

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.of(tarefa));

        service.removeEtiquetaFromTarefa(tarefaId, etiquetaId);

        Assertions.assertTrue(tarefa.getEtiquetas().isEmpty());

        Mockito.verify(repository, Mockito.times(1)).save(tarefa);
    }

    @DisplayName("Deve lançar ResourceNotFoundException quando a tarefa não for encontrada ao remover etiqueta.")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenRemoveEtiquetaFromTarefaNotFound() {
        Long tarefaId = ID;
        Long etiquetaId = 1L;

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> service.removeEtiquetaFromTarefa(tarefaId, etiquetaId)
        );

        Assertions.assertEquals("Tarefa não encontrada, id: " + tarefaId, exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Tarefa.class));
    }

    @DisplayName("Deve lançar BusinessException quando a etiqueta não estiver associada à tarefa.")
    @Test
    public void shouldThrowBusinessExceptionWhenEtiquetaNotAssociated() {
        Long tarefaId = ID;
        Long etiquetaId = 1L;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        tarefa.setEtiquetas(new HashSet<>());

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.of(tarefa));

        BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> service.removeEtiquetaFromTarefa(tarefaId, etiquetaId)
        );

        Assertions.assertEquals("Etiqueta com ID " + etiquetaId + " não está associada à tarefa", exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Tarefa.class));
    }

    @DisplayName("Deve retornar todas as etiquetas de uma tarefa")
    @Test
    public void shouldReturnAllEtiquetasByTarefaId() {
        Long tarefaId = 1L;

        Etiqueta etiqueta1 = new Etiqueta();
        etiqueta1.setId(1L);
        etiqueta1.setNome("Etiqueta 1");

        Etiqueta etiqueta2 = new Etiqueta();
        etiqueta2.setId(2L);
        etiqueta2.setNome("Etiqueta 2");

        EtiquetaResponseDTO dto1 = new EtiquetaResponseDTO();
        dto1.setId(1L);
        dto1.setNome("Etiqueta 1");

        EtiquetaResponseDTO dto2 = new EtiquetaResponseDTO();
        dto2.setId(2L);
        dto2.setNome("Etiqueta 2");

        Mockito.when(etiquetaRepository.findAllByTarefasId(tarefaId))
                .thenReturn(List.of(etiqueta1, etiqueta2));

        Mockito.when(etiquetaMapper.toDto(etiqueta1)).thenReturn(dto1);
        Mockito.when(etiquetaMapper.toDto(etiqueta2)).thenReturn(dto2);

        List<EtiquetaResponseDTO> responseDTOs = service.findAllEtiquetasByTarefaId(tarefaId);

        Assertions.assertNotNull(responseDTOs);
        Assertions.assertEquals(2, responseDTOs.size());

        Assertions.assertEquals("Etiqueta 1", responseDTOs.get(0).getNome());
        Assertions.assertEquals("Etiqueta 2", responseDTOs.get(1).getNome());

        Mockito.verify(etiquetaRepository, Mockito.times(1)).findAllByTarefasId(tarefaId);
        Mockito.verify(etiquetaMapper, Mockito.times(1)).toDto(etiqueta1);
        Mockito.verify(etiquetaMapper, Mockito.times(1)).toDto(etiqueta2);
    }

    @DisplayName("Deve adicionar categoria à tarefa com sucesso.")
    @Test
    public void shouldAddCategoriaToTarefaSuccessfully() {
        Long tarefaId = ID;
        Long categoriaId = 1L;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        tarefa.setCategorias(new HashSet<>());

        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        Mockito.when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        Mockito.when(repository.save(Mockito.any(Tarefa.class))).thenReturn(tarefa);
        Mockito.when(tarefaMapper.toDto(Mockito.any(Tarefa.class))).thenReturn(new TarefaResponseDTO());

        TarefaResponseDTO responseDTO = service.addCategoriaToTarefa(tarefaId, categoriaId);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertTrue(tarefa.getCategorias().contains(categoria));

        Mockito.verify(repository, Mockito.times(1)).save(tarefa);
    }

    @DisplayName("Deve lançar ResourceNotFoundException quando a tarefa não for encontrada ao adicionar categoria.")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenAddCategoryToTarefaNotFound() {
        Long tarefaId = ID;
        Long categoriaId = 1L;

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> service.addCategoriaToTarefa(tarefaId, categoriaId)
        );

        Assertions.assertEquals("Tarefa não encontrada, id: " + tarefaId, exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Tarefa.class));
    }

    @DisplayName("Deve lançar ResourceNotFoundException quando a categoria não for encontrada ao adicionar em tarefa.")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenCategoriaNotFound() {
        Long tarefaId = ID;
        Long categoriaId = 1L;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        Mockito.when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> service.addCategoriaToTarefa(tarefaId, categoriaId)
        );

        Assertions.assertEquals("Categoria não encontrada, id: " + categoriaId, exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Tarefa.class));
    }

    @DisplayName("Deve remover categoria da tarefa com sucesso.")
    @Test
    public void shouldRemoveCategoriaFromTarefaSuccessfully() {
        Long tarefaId = ID;
        Long categoriaId = 1L;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        tarefa.setCategorias(new HashSet<>(List.of(categoria)));

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        Mockito.when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
        Mockito.when(repository.save(Mockito.any(Tarefa.class))).thenReturn(tarefa);
        Mockito.when(tarefaMapper.toDto(Mockito.any(Tarefa.class))).thenReturn(new TarefaResponseDTO());

        TarefaResponseDTO responseDTO = service.removeCategoriaFromTarefa(tarefaId, categoriaId);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertFalse(tarefa.getCategorias().contains(categoria));

        Mockito.verify(repository, Mockito.times(1)).save(tarefa);
    }

    @DisplayName("Deve lançar ResourceNotFoundException quando a tarefa não for encontrada ao remover tarefa.")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenRemoveCategoryTarefaNotFound() {
        Long tarefaId = ID;
        Long categoriaId = 1L;

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> service.removeCategoriaFromTarefa(tarefaId, categoriaId)
        );

        Assertions.assertEquals("Tarefa não encontrada, id: " + tarefaId, exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Tarefa.class));
    }

    @DisplayName("Deve lançar ResourceNotFoundException quando a categoria não for encontrada ao remover.")
    @Test
    public void shouldThrowResourceNotFoundExceptionWhenRemoveCategoriaNotFound() {
        Long tarefaId = ID;
        Long categoriaId = 1L;

        Tarefa tarefa = new Tarefa();
        tarefa.setId(tarefaId);

        Mockito.when(repository.findById(tarefaId)).thenReturn(Optional.of(tarefa));
        Mockito.when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> service.removeCategoriaFromTarefa(tarefaId, categoriaId)
        );

        Assertions.assertEquals("Categoria não encontrada, id: " + categoriaId, exception.getMessage());

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Tarefa.class));
    }

}

