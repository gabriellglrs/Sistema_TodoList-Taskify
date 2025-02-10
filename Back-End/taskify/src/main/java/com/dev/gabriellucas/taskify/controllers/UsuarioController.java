package com.dev.gabriellucas.taskify.controllers;

import com.dev.gabriellucas.taskify.DTO.*;
import com.dev.gabriellucas.taskify.services.impl.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


     @Operation(summary = "Cria um novo usuário", description = "Cria um novo usuário com os dados fornecidos.")
     @ApiResponses(value = {
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos")
     })
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

     @Operation(summary = "Busca um usuário pelo ID", description = "Retorna os dados do usuário correspondente ao ID fornecido.")
     @ApiResponses(value = {
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuário encontrado"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado")
     })
     @GetMapping("/{id}")
     public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
          UsuarioResponseDTO responseDTO = usuarioService.findByIdUsuario(id);
          return ResponseEntity.ok(responseDTO);
     }

     @Operation(summary = "Atualiza um usuário pelo ID", description = "Atualiza os dados do usuário correspondente ao ID fornecido.")
     @ApiResponses(value = {
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos")
     })
     @PutMapping("/{id}")
     public ResponseEntity<UsuarioResponseDTO> upadate(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO requestDTO) {
          UsuarioResponseDTO responseDTO = usuarioService.updateUsuario(id, requestDTO);
          return ResponseEntity.ok(responseDTO);
     }

     @Operation(summary = "Atualiza parcialmente um usuário pelo ID", description = "Atualiza parcialmente os dados do usuário correspondente ao ID fornecido.")
     @ApiResponses(value = {
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Dados inválidos")
     })
     @PatchMapping("/{id}")
     public ResponseEntity<UsuarioResponseDTO> upadateParcial(@PathVariable Long id, @Valid @RequestBody UsuarioRequestPatchDTO requestDTO) {
          UsuarioResponseDTO responseDTO = usuarioService.updateParcialUsuario(id, requestDTO);
          return ResponseEntity.ok(responseDTO);
     }

     @Operation(summary = "Deleta um usuário pelo ID", description = "Deleta o usuário correspondente ao ID fornecido.")
     @ApiResponses(value = {
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado")
     })
     @DeleteMapping("/{id}")
     public ResponseEntity<Void> delete(@PathVariable Long id) {
          usuarioService.deleteByIdUsuario(id);
          return ResponseEntity.noContent().build();
     }

     @Operation(summary = "Busca listas de um usuário pelo ID", description = "Retorna as listas do usuário correspondente ao ID fornecido.")
     @ApiResponses(value = {
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Listas encontradas"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado")
     })
     @GetMapping("{id}/listas")
     public ResponseEntity<List<ListaResponseDTO>> findByUsuarioListas(@PathVariable Long id) {
          List<ListaResponseDTO> listas = usuarioService.findByUsuarioListas(id);
          return ResponseEntity.ok(listas);
     }

     @Operation(summary = "Busca notificações de um usuário pelo ID", description = "Retorna as notificações do usuário correspondente ao ID fornecido.")
     @ApiResponses(value = {
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Notificações encontradas"),
             @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuário não encontrado")
     })
     @GetMapping("{id}/notificacoes")
     public ResponseEntity<List<NotificacaoResponseDTO>> findByUsuarioNotificacoes(@PathVariable Long id) {
          List<NotificacaoResponseDTO> notificacoes = usuarioService.findByUsuarioNotificacoes(id);
          return ResponseEntity.ok(notificacoes);
     }
}
