package com.dev.gabriellucas.taskify.exceptions.handler;

import com.dev.gabriellucas.taskify.exceptions.DatabaseException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.DTO.ErroResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
     @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<ErroResponseDTO> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest httpServletRequest) {

                  ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(Instant.now())
                  .status(HttpStatus.NOT_FOUND.value())
                  .code(HttpStatus.NOT_FOUND.name())
                  .message(exception.getMessage())
                  .details("Não foi possível localizar o ID fornecido. Por favor, verifique o ID e tente novamente. Se o problema persistir, entre em contato com o suporte.")
                  .path(httpServletRequest.getRequestURI())
                  .build();
          return ResponseEntity.status(erroResponseDTO.getStatus()).body(erroResponseDTO);
     }

     @ExceptionHandler(DatabaseException.class)
     public ResponseEntity<ErroResponseDTO> database(DatabaseException exception, HttpServletRequest httpServletRequest) {

          ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(Instant.now())
                  .status(HttpStatus.BAD_REQUEST.value())
                  .code(HttpStatus.BAD_REQUEST.name())
                  .message(exception.getMessage())
                  .details("A entidade não pode ser deletada porque está associada com outra entidade. Para prosseguir com a exclusão, certifique-se de remover ou desassociar todas as entidades relacionadas.")
                  .path(httpServletRequest.getRequestURI())
                  .build();
          return ResponseEntity.status(erroResponseDTO.getStatus()).body(erroResponseDTO);
     }

     // Tratamento de validações de DTO
     @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {

          Map<String, String> errors = new HashMap<>();

          // Captura todos os erros de validação e adiciona ao mapa
          for (FieldError error : ex.getBindingResult().getFieldErrors()) {
               errors.put(error.getField(), error.getDefaultMessage());
          }

          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
     }

     // Tratamento de outras exceções genéricas
     @ExceptionHandler(Exception.class)
     public ResponseEntity<String> handleGenericException(Exception ex) {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + ex.getMessage());
     }

}
