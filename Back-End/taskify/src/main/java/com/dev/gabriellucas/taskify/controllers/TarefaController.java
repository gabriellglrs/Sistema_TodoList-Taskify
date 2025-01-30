package com.dev.gabriellucas.taskify.controllers;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.entities.Anexo;
import com.dev.gabriellucas.taskify.services.AnexoService;
import com.dev.gabriellucas.taskify.services.TarefaService;
import com.dev.gabriellucas.taskify.services.impl.TarefaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/tarefas")
public class TarefaController {
    private final TarefaService service;
    private final AnexoService anexoService;

    public TarefaController(TarefaService service, AnexoService anexoService) {
        this.service = service;
        this.anexoService = anexoService;
    }

    @PostMapping(value = "/{idLista}")
    public ResponseEntity<TarefaResponseDTO> saveTarefa(@Valid @RequestBody TarefaRequestDTO request, @PathVariable Long idLista) {
        TarefaResponseDTO responseDTO = service.saveTarefa(request, idLista);
        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(responseDTO.getId())
                        .toUri())
                .body(responseDTO);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TarefaResponseDTO> findTarefaById(@PathVariable Long id) {
        TarefaResponseDTO responseDTO = service.findTarefaById(id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TarefaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody TarefaRequestDTO request) {
        TarefaResponseDTO responseDTO = service.updateTarefa(request, id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<TarefaResponseDTO> updateParcial(@PathVariable Long id, @Valid @RequestBody TarefaRequestPatchDTO request) {
        TarefaResponseDTO responseDTO = service.updateTarefaParcial(request, id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping(value = "/{id}/concluir")
    public ResponseEntity<TarefaResponseDTO> completeTarefa(@PathVariable Long id) {
        TarefaResponseDTO responseDTO = service.completeTarefa(id);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(value = "/{id}/comentarios")
    public ResponseEntity<List<ComentarioResponseDTO>> findAllCommentsByTarefaId(@PathVariable Long id) {
        List<ComentarioResponseDTO> comentarios = service.findAllCommentsByTarefaId(id);
        return ResponseEntity.ok().body(comentarios);
    }

    @GetMapping(value = "/{id}/anexos")
    public ResponseEntity<List<AnexoResponseDTO>> findAllAnexosByTarefaId(@PathVariable Long id) {
        List<AnexoResponseDTO> anexos = service.findAllAnexosByTarefaId(id);
        return ResponseEntity.ok().body(anexos);
    }

    @GetMapping(value = "/{id}/historico")
    public ResponseEntity<List<HistoricoResponseDTO>> findAllHistoricosByTarefaId(@PathVariable Long id) {
        List<HistoricoResponseDTO> historicos = service.findAllHistoricosByTarefaId(id);
        return ResponseEntity.ok().body(historicos);
    }

    @PostMapping("/{id}/etiquetas")
    public ResponseEntity<Void> addEtiquetasToTarefa(
            @PathVariable Long id,
            @RequestBody List<EtiquetaInsertRequestDTO> etiquetas) {
        service.addEtiquetaToTarefa(id, etiquetas);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idTarefa}/etiquetas/{idEtiqueta}")
    public ResponseEntity<Void> removeEtiquetaFromTarefa(
            @PathVariable Long idTarefa,
            @PathVariable Long idEtiqueta) {
        service.removeEtiquetaFromTarefa(idTarefa, idEtiqueta);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/anexos")
    public ResponseEntity<AnexoResponseDTO> uploadfile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Anexo attachment = null;
        String downloadUrl = "";
        attachment = anexoService.saveAnexo(file, id);
        downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId().toString())
                .toUriString();
        AnexoResponseDTO responseDTO = AnexoResponseDTO.builder()
                .id(attachment.getId())
                .nome(attachment.getNome())
                .tipo(attachment.getTipo())
                .tamanho(attachment.getTamanho())
                .url(downloadUrl)
                .build();
        return ResponseEntity.ok(responseDTO);
    }

}
