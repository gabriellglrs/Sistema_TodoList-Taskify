package com.dev.gabriellucas.taskify.controller;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.controllers.TarefaController;
import com.dev.gabriellucas.taskify.entities.Anexo;
import com.dev.gabriellucas.taskify.provider.TarefaRequestDTOProvider;
import com.dev.gabriellucas.taskify.provider.TarefaRequestPatchDTOProvider;
import com.dev.gabriellucas.taskify.provider.TarefaResponseDTOProvider;
import com.dev.gabriellucas.taskify.services.AnexoService;
import com.dev.gabriellucas.taskify.services.TarefaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TarefaControllerTest {

    private static final Long ID = 1L;

    @InjectMocks
    private TarefaController controller;

    @Mock
    private TarefaService service;

    @Mock
    private AnexoService anexoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private TarefaResponseDTOProvider tarefaResponseDTOProvider;
    private TarefaRequestDTOProvider tarefaRequestDTOProvider;
    private TarefaRequestPatchDTOProvider tarefaRequestPatchDTOProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tarefaResponseDTOProvider = new TarefaResponseDTOProvider();
        tarefaRequestDTOProvider = new TarefaRequestDTOProvider();
        tarefaRequestPatchDTOProvider = new TarefaRequestPatchDTOProvider();
        controller = new TarefaController(service, anexoService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @DisplayName("Deve criar uma tarefa com sucesso. ")
    @Test
    public void shouldReturnCreatedTarefaOnController() throws Exception {
        Long idLista = ID;

        TarefaResponseDTO tarefaDTO = tarefaResponseDTOProvider.create();
        TarefaRequestDTO tarefaRequestDTO= tarefaRequestDTOProvider.create();

        Mockito.when(service.saveTarefa(Mockito.any(TarefaRequestDTO.class), Mockito.eq(idLista))).thenReturn(tarefaDTO);

        mockMvc.perform(post("/api/tarefas/{idLista}", idLista)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefaRequestDTO)))
                .andExpect(status().isCreated());
    }

    @DisplayName("Deve retornar uma tarefa. ")
    @Test
    public void shouldControllerReturnFindByIdWithSuccess() {
        TarefaResponseDTO tarefaDTO = tarefaResponseDTOProvider.create();

        Mockito.when(service.findTarefaById(Mockito.anyLong())).thenReturn(tarefaDTO);

        ResponseEntity<TarefaResponseDTO> response = controller.findTarefaById(Mockito.anyLong());

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(TarefaResponseDTO.class, response.getBody().getClass());
    }

    @DisplayName("Deve atualizar a tarefa. ")
    @Test
    public void shouldReturnUpdateTarefa() {
        Long idTarefa = ID;

        TarefaResponseDTO tarefaDTO = tarefaResponseDTOProvider.create();
        TarefaRequestDTO tarefaRequestDTO = tarefaRequestDTOProvider.create();

        Mockito.when(service.updateTarefa(Mockito.any(TarefaRequestDTO.class), Mockito.eq(idTarefa))).thenReturn(tarefaDTO);

        ResponseEntity<TarefaResponseDTO> response = controller.update(idTarefa, tarefaRequestDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(service, Mockito.times(1)).updateTarefa(Mockito.any(TarefaRequestDTO.class), Mockito.eq(idTarefa));
    }

    @DisplayName("Deve atualizar a tarefa parcial. ")
    @Test
    public void shouldReturnUpdateTarefaParcial() {
        Long idTarefa = ID;

        TarefaResponseDTO tarefaDTO = tarefaResponseDTOProvider.create();
        TarefaRequestPatchDTO tarefaRequestPatchDTO = tarefaRequestPatchDTOProvider.create();

        Mockito.when(service.updateTarefaParcial(Mockito.any(TarefaRequestPatchDTO.class), Mockito.eq(idTarefa))).thenReturn(tarefaDTO);

        ResponseEntity<TarefaResponseDTO> response = controller.updateParcial(idTarefa, tarefaRequestPatchDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(service, Mockito.times(1)).updateTarefaParcial(Mockito.any(TarefaRequestPatchDTO.class), Mockito.eq(idTarefa));
    }

    @DisplayName("Deve atualizar a tarefa para concluído. ")
    @Test
    public void shouldReturnUpdateCompleteTarefa() {
        Long idTarefa = ID;

        TarefaResponseDTO tarefaDTO = tarefaResponseDTOProvider.create();

        Mockito.when(service.completeTarefa(Mockito.eq(idTarefa))).thenReturn(tarefaDTO);

        ResponseEntity<TarefaResponseDTO> response = controller.completeTarefa(idTarefa);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(service, Mockito.times(1)).completeTarefa(Mockito.eq(idTarefa));
    }

    @Test
    @DisplayName("Deve retornar todos os comentários da tarefa com sucesso")
    public void shouldReturnAllCommentsByTarefaId() throws Exception {
        ComentarioResponseDTO comentario = new ComentarioResponseDTO();
        Mockito.when(service.findAllCommentsByTarefaId(ID)).thenReturn(List.of(comentario));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tarefas/" + ID + "/comentarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    @DisplayName("Deve retornar todos os anexos da tarefa com sucesso")
    public void shouldReturnAllAnexosByTarefaId() throws Exception {
        AnexoResponseDTO anexo = new AnexoResponseDTO();
        Mockito.when(service.findAllAnexosByTarefaId(ID)).thenReturn(List.of(anexo));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tarefas/" + ID + "/anexos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    @DisplayName("Deve retornar todo o histórico da tarefa com sucesso")
    public void shouldReturnAllHistoricosByTarefaId() throws Exception {
        HistoricoResponseDTO historico = new HistoricoResponseDTO();
        Mockito.when(service.findAllHistoricosByTarefaId(ID)).thenReturn(List.of(historico));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tarefas/" + ID + "/historico")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    @DisplayName("Deve retornar todas as etiquetas da tarefa com sucesso")
    public void shouldReturnAllEtiquetasByTarefaId() throws Exception {
        EtiquetaResponseDTO etiqueta = new EtiquetaResponseDTO();
        Mockito.when(service.findAllEtiquetasByTarefaId(ID)).thenReturn(List.of(etiqueta));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tarefas/" + ID + "/etiquetas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    @DisplayName("Deve adicionar etiquetas a tarefa")
    public void shouldAddEtiquetaToTarefa() {
        List<EtiquetaInsertRequestDTO> etiquetas = new ArrayList<>();

        Mockito.doNothing().when(service).addEtiquetaToTarefa(ID, etiquetas);

        ResponseEntity<Void> response = controller.addEtiquetasToTarefa(ID, etiquetas);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(service, Mockito.times(1)).addEtiquetaToTarefa(ID, etiquetas);
    }

    @Test
    @DisplayName("Deve remover etiquetas das tarefas")
    public void shouldRemevoEtiquetaFromTarefas() {
        Mockito.doNothing().when(service).removeEtiquetaFromTarefa(ID, ID);

        ResponseEntity<Void> response = controller.removeEtiquetaFromTarefa(ID, ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Mockito.verify(service, Mockito.times(1)).removeEtiquetaFromTarefa(ID, ID);
    }

    @Test
    @DisplayName("Deve adicionar categoria a tarefa")
    public void shouldAddCategoriaToTarefa() {
        TarefaResponseDTO tarefaDTO = tarefaResponseDTOProvider.create();

        Mockito.when(service.addCategoriaToTarefa(Mockito.eq(ID), Mockito.eq(ID))).thenReturn(tarefaDTO);

        ResponseEntity<TarefaResponseDTO> response = controller.addCategoriaToTarefa(ID, ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(service, Mockito.times(1)).addCategoriaToTarefa(Mockito.eq(ID), Mockito.eq(ID));
    }

    @Test
    @DisplayName("Deve remover categorias das tarefas")
    public void shouldRemevoCategoriaFromTarefas() {
        TarefaResponseDTO tarefaDTO = tarefaResponseDTOProvider.create();

        Mockito.when(service.removeCategoriaFromTarefa(Mockito.eq(ID), Mockito.eq(ID))).thenReturn(tarefaDTO);

        ResponseEntity<TarefaResponseDTO> response = controller.removeCategoriaFromTarefa(ID, ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(service, Mockito.times(1)).removeCategoriaFromTarefa(Mockito.eq(ID), Mockito.eq(ID));
    }

    @Test
    @DisplayName("Deve fazer upload de anexo com sucesso")
    public void shouldUploadFileSuccessfully() throws Exception {
        Long ID = 1L;

        Anexo anexo = new Anexo();
        anexo.setId(100L);
        anexo.setNome("documento.pdf");
        anexo.setTipo("application/pdf");
        anexo.setTamanho(2048L);

        Mockito.doReturn(anexo)
                .when(anexoService)
                .saveAnexo(Mockito.any(MultipartFile.class), Mockito.eq(ID));

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "documento.pdf",
                "application/pdf",
                "conteúdo do arquivo".getBytes()
        );

        mockMvc.perform(multipart("/api/tarefas/{id}/anexos", ID)
                        .file(mockFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(anexo.getNome()))
                .andExpect(jsonPath("$.tipo").value(anexo.getTipo()))
                .andExpect(jsonPath("$.tamanho").value(anexo.getTamanho()));

        Mockito.verify(anexoService, Mockito.times(1)).saveAnexo(Mockito.any(MultipartFile.class), Mockito.eq(ID));

    }

}
