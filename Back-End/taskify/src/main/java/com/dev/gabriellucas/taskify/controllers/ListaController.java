package com.dev.gabriellucas.taskify.controllers;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.services.impl.ListaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/listas")
public class ListaController {

    @Autowired
    private ListaServiceImpl listaService;

    @PostMapping
    public ResponseEntity<ListaResponseDTO> save(@Valid @RequestBody ListaRequestDTO requestDTO) {
        ListaResponseDTO responseDTO = listaService.saveLista(requestDTO);
        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest() // Pega a URI atual
                        .path("/{id}") // Adiciona o ID do novo produto à URI
                        .buildAndExpand(responseDTO.getId()) // Expande o {id} com o ID real
                        .toUri()) // Constrói a URI completa
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaResponseDTO> findById(@PathVariable Long id) {
        ListaResponseDTO responseDTO = listaService.findByIdLista(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ListaRequestDTO requestDTO) {
        ListaResponseDTO responseDTO = listaService.updateLista(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ListaResponseDTO> updateParcial(@PathVariable Long id, @Valid @RequestBody ListaRequestPatchDTO requestDTO) {
        ListaResponseDTO responseDTO = listaService.updateParcialLista(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}/arquivar/{userId}")
    public ResponseEntity<Void> archiveLista(@PathVariable Long id, @PathVariable Long userId) {
        listaService.archiveLista(id, userId);
        return ResponseEntity.noContent().build();
    }
}
