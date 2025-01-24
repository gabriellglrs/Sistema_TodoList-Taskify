package com.dev.gabriellucas.taskify.controllers.handler;

import com.dev.gabriellucas.taskify.exceptions.DatabaseException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.DTO.ErroResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
     @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<ErroResponseDTO> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest httpServletRequest) {

          // Extrair o ID do usuário do caminho da URI
          String path = httpServletRequest.getRequestURI();
          String userId = path.substring(path.lastIndexOf('/') + 1);
          String resourceName = path.substring(path.lastIndexOf('/', path.lastIndexOf('/') - 1) + 1, path.lastIndexOf('/'));

          // Remover o 's' final, se existir
          if (resourceName.endsWith("s")) {
               resourceName = resourceName.substring(0, resourceName.length() - 1);
          }

                  ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(Instant.now())
                  .status(HttpStatus.NOT_FOUND.value())
                  .code(HttpStatus.NOT_FOUND.name())
                  .message(exception.getMessage())
                  .details("Não foi possível localizar um " + resourceName + " com o ID: " + userId + " fornecido. Por favor, verifique o ID e tente novamente. Se o problema persistir, entre em contato com o suporte.")
                  .path(httpServletRequest.getRequestURI())
                  .build();
          return ResponseEntity.status(erroResponseDTO.getStatus()).body(erroResponseDTO);
     }

     @ExceptionHandler(DatabaseException.class)
     public ResponseEntity<ErroResponseDTO> database(DatabaseException exception, HttpServletRequest httpServletRequest) {

          // Extrair o ID do usuário do caminho da URI
          String path = httpServletRequest.getRequestURI();
          String userId = path.substring(path.lastIndexOf('/') + 1);
          String resourceName = path.substring(path.lastIndexOf('/', path.lastIndexOf('/') - 1) + 1, path.lastIndexOf('/'));

          // Remover o 's' final, se existir
          if (resourceName.endsWith("s")) {
               resourceName = resourceName.substring(0, resourceName.length() - 1);
          }

          ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(Instant.now())
                  .status(HttpStatus.BAD_REQUEST.value())
                  .code(HttpStatus.BAD_REQUEST.name())
                  .message(exception.getMessage())
                  .details("A entidade " + resourceName + " com ID: " + userId + " não pode ser deletada porque está associada com outra entidade. Para prosseguir com a exclusão, certifique-se de remover ou desassociar todas as entidades relacionadas.")
                  .path(path)
                  .build();
          return ResponseEntity.status(erroResponseDTO.getStatus()).body(erroResponseDTO);
     }

}
