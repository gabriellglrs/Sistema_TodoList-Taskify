package com.dev.gabriellucas.taskify.controllers;

import com.dev.gabriellucas.taskify.DTO.ClientRequestDTO;
import com.dev.gabriellucas.taskify.DTO.ClientResponseDTO;
import com.dev.gabriellucas.taskify.services.impl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
     private final ClientServiceImpl clientService;

     @PostMapping
     @PreAuthorize("hasRole('ADMIN')")
     public ResponseEntity<ClientResponseDTO> saveClient(@RequestBody ClientRequestDTO request) {
          ClientResponseDTO responseDTO = clientService.Salvar(request);
          return ResponseEntity
                  .created(ServletUriComponentsBuilder
                          .fromCurrentRequest() // Pega a URI atual
                          .path("/{id}") // Adiciona o ID do novo produto à URI
                          .buildAndExpand(responseDTO.getId()) // Expande o {id} com o ID real
                          .toUri()) // Constrói a URI completa
                  .body(responseDTO);
     }
}
