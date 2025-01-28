package com.dev.gabriellucas.taskify.controllers;

import com.dev.gabriellucas.taskify.DTO.CategoriaRequestDTO;
import com.dev.gabriellucas.taskify.DTO.CategoriaResponseDTO;
import com.dev.gabriellucas.taskify.services.impl.CategoriaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
     private final CategoriaServiceImpl service;

     @Autowired
     public CategoriaController(CategoriaServiceImpl service) {
          this.service = service;
     }

     @PostMapping
     public ResponseEntity<CategoriaResponseDTO> saveCategoria(@RequestBody @Valid CategoriaRequestDTO requestDTO) {
          CategoriaResponseDTO responseDTO = service.saveCategoria(requestDTO);
          return ResponseEntity.created(ServletUriComponentsBuilder
                          .fromCurrentRequest()
                          .path("/{id}")
                          .buildAndExpand(responseDTO.getId())
                          .toUri())
                  .body(responseDTO);
     }

     @GetMapping("/{id}")
     public ResponseEntity<CategoriaResponseDTO> findByIdCategoria(@PathVariable Long id) {
          CategoriaResponseDTO responseDTO = service.findByIdCategoria(id);
          return ResponseEntity.ok(responseDTO);
     }
}
