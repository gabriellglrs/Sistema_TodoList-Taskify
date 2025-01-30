package com.dev.gabriellucas.taskify.controllers;

import com.dev.gabriellucas.taskify.entities.Anexo;
import com.dev.gabriellucas.taskify.services.AnexoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/anexos")
@RequiredArgsConstructor
public class AnexoController {

    private final AnexoService service;

    @GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Anexo> findByAnexoId(@PathVariable Long id) {
        Anexo anexoResponseDTO = service.getAnexoById(id);
        return ResponseEntity.ok().body(anexoResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws Exception {
        Anexo fileUpload = service.getAnexoById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileUpload.getTipo()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileUpload; filename=\""+ fileUpload.getNome()
                        +"\"").body(new ByteArrayResource(fileUpload.getDados()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAnexo(@PathVariable Long id) {
        service.deleteAnexo(id);
        return ResponseEntity.noContent().build();
    }

}
