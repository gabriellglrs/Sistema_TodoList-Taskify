package com.dev.gabriellucas.taskify.controllers;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.services.impl.UsuarioServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/api/usuarios")
public class UsuarioController {

     private UsuarioServiceImpl usuarioService;

     @Autowired
     public UsuarioController(UsuarioServiceImpl usuarioService) {
          this.usuarioService = usuarioService;
     }

     @PostMapping
     public ResponseEntity<UsuarioResponseDTO> save(@Valid @RequestBody UsuarioRequestDTO requestDTO) {
          UsuarioResponseDTO responseDTO = usuarioService.saveUsuario(requestDTO);
          return ResponseEntity
                  .created(ServletUriComponentsBuilder
                          .fromCurrentRequest() // Pega a URI atual
                          .path("/{id}") // Adiciona o ID do novo produto à URI
                          .buildAndExpand(responseDTO) // Expande o {id} com o ID real
                          .toUri()) // Constrói a URI completa
                  .body(responseDTO);
     }

     @GetMapping("/{id}")
     public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
          UsuarioResponseDTO responseDTO = usuarioService.findByIdUsuario(id);
          return ResponseEntity.ok(responseDTO);
     }

     @PutMapping("/{id}")
     public ResponseEntity<UsuarioResponseDTO> upadate(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO requestDTO) {
          UsuarioResponseDTO responseDTO = usuarioService.updateUsuario(id, requestDTO);
          return ResponseEntity.ok(responseDTO);
     }

     @PatchMapping("/{id}")
     public ResponseEntity<UsuarioResponseDTO> upadateParcial(@PathVariable Long id, @Valid @RequestBody UsuarioRequestPatchDTO requestDTO) {
          UsuarioResponseDTO responseDTO = usuarioService.updateParcialUsuario(id, requestDTO);
          return ResponseEntity.ok(responseDTO);
     }

     @DeleteMapping("/{id}")
     public ResponseEntity<Void> delete(@PathVariable Long id) {
          usuarioService.deleteByIdUsuario(id);
          return ResponseEntity.noContent().build();
     }

     @GetMapping("{id}/listas")
     public ResponseEntity<List<ListaResponseDTO>> findByUsuarioListas(@PathVariable Long id) {
          List<ListaResponseDTO> listas = usuarioService.findByUsuarioListas(id);
          return ResponseEntity.ok(listas);
     }

     @GetMapping("{id}/notificacoes")
     public ResponseEntity<List<NotificacaoResponseDTO>> findByUsuarioNotificacoes(@PathVariable Long id) {
          List<NotificacaoResponseDTO> notificacoes = usuarioService.findByUsuarioNotificacoes(id);
          return ResponseEntity.ok(notificacoes);
     }
}
