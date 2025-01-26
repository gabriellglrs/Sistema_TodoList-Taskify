package com.dev.gabriellucas.taskify.controllers;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.services.TarefaService;
import com.dev.gabriellucas.taskify.services.impl.TarefaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tarefas")
public class TarefaController {
    private final TarefaService service;

    public TarefaController(TarefaService service) {
        this.service = service;
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

    @PatchMapping(value = "/{id}/concluir")
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


}
