package com.dev.gabriellucas.taskify.controllers;

import com.dev.gabriellucas.taskify.DTO.EtiquetaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.EtiquetaRequestPatchDTO;
import com.dev.gabriellucas.taskify.DTO.EtiquetaResponseDTO;
import com.dev.gabriellucas.taskify.services.EtiquetaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/etiquetas")
@RequiredArgsConstructor
public class EtiquetaController {

    private final EtiquetaService service;

    @PostMapping
    public ResponseEntity<EtiquetaResponseDTO> saveEtiqueta(@Valid @RequestBody EtiquetaRequestDTO request) {
        EtiquetaResponseDTO objDto = service.saveEtiqueta(request);
        return ResponseEntity.created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(objDto.getId())
                        .toUri())
                .body(objDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EtiquetaResponseDTO> findEtiquetaById(@PathVariable Long id) {
        EtiquetaResponseDTO response = service.findEtiquetaById(id);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EtiquetaResponseDTO> updateEtiqueta(@PathVariable Long id, @Valid @RequestBody EtiquetaRequestDTO request ) {
        EtiquetaResponseDTO response = service.updateEtiqueta(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEtiqueta(@PathVariable Long id) {
        service.deleteEtiqueta(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<EtiquetaResponseDTO> updateEtiquetaParcial(@PathVariable Long id, @Valid @RequestBody EtiquetaRequestPatchDTO request) {
        EtiquetaResponseDTO responseDTO = service.updateEtiquetaParcial(id, request);
        return  ResponseEntity.ok().body(responseDTO);
    }
}
