package com.dev.gabriellucas.taskify.exceptions.handler;

import com.dev.gabriellucas.taskify.exceptions.BusinessException;
import com.dev.gabriellucas.taskify.exceptions.DatabaseException;
import com.dev.gabriellucas.taskify.exceptions.ResourceNotFoundException;
import com.dev.gabriellucas.taskify.DTO.ErroResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
     @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<ErroResponseDTO> resourceNotFound(ResourceNotFoundException exception, HttpServletRequest httpServletRequest) {

          ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(LocalDateTime.now())
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
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.BAD_REQUEST.value())
                  .code(HttpStatus.BAD_REQUEST.name())
                  .message(exception.getMessage())
                  .details("A entidade não pode ser deletada porque está associada com outra entidade. Para prosseguir com a exclusão, certifique-se de remover ou desassociar todas as entidades relacionadas.")
                  .path(httpServletRequest.getRequestURI())
                  .build();
          return ResponseEntity.status(erroResponseDTO.getStatus()).body(erroResponseDTO);
     }

     @ExceptionHandler(BusinessException.class)
     public ResponseEntity<ErroResponseDTO> business(BusinessException exception, HttpServletRequest httpServletRequest) {
          ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                  .code(HttpStatus.UNPROCESSABLE_ENTITY.name())
                  .message(exception.getMessage())
                  .details("Violações de regras de negócio")
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
     public ResponseEntity<String> handleGenericException(Exception exception, HttpServletRequest httpServletRequest) {
          ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                  .code(HttpStatus.INTERNAL_SERVER_ERROR.name())
                  .message(exception.getMessage())
                  .details("Erro inesperado!")
                  .path(httpServletRequest.getRequestURI())
                  .build();
          return ResponseEntity.status(erroResponseDTO.getStatus()).body(erroResponseDTO.toString());
     }

     @ExceptionHandler(MaxUploadSizeExceededException.class)
     public ResponseEntity<ErroResponseDTO> handleMaxUploadSizeExceededException(
             MaxUploadSizeExceededException exception, HttpServletRequest request) {

          ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.PAYLOAD_TOO_LARGE.value())
                  .code(HttpStatus.PAYLOAD_TOO_LARGE.name())
                  .message("Tamanho máximo de 10MB excedido, tente novamente com um arquivo menor.")
                  .details("O arquivo enviado excede o limite permitido.")
                  .path(request.getRequestURI())
                  .build();

          return ResponseEntity.status(erroResponseDTO.getStatus()).body(erroResponseDTO);
     }

     @ExceptionHandler(AccessDeniedException.class)
     public ResponseEntity<ErroResponseDTO> accessDeniedException(
             AccessDeniedException exception, HttpServletRequest request) {

          ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.FORBIDDEN.value())
                  .code(exception.getMessage())
                  .message("Acesso negado! Você não tem permissão para esta ação.")
                  .details("Seu usuário não possui privilégios suficientes para acessar este recurso. Caso necessite, entre em contato com o administrador do sistema.")
                  .path(request.getRequestURI())
                  .build();

          return ResponseEntity.status(erroResponseDTO.getStatus()).body(erroResponseDTO);
     }

     @ExceptionHandler(AuthenticationException.class)
     public ResponseEntity<ErroResponseDTO> authenticationException(
             AuthenticationException exception,
             HttpServletRequest request) {

          ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.UNAUTHORIZED.value())
                  .code(HttpStatus.UNAUTHORIZED.name())
                  .message("Não autorizado! Autenticação é necessária para acessar este recurso.")
                  .details("Sua sessão pode ter expirado ou você não forneceu credenciais válidas. " +
                           "Por favor, faça login novamente.")
                  .path(request.getRequestURI())
                  .build();

          return ResponseEntity.status(erroResponseDTO.getStatus())
                  .body(erroResponseDTO);
     }

     @ExceptionHandler(BadCredentialsException.class)
     public ResponseEntity<ErroResponseDTO> badCredentialsException(
             BadCredentialsException exception,
             HttpServletRequest request) {

          ErroResponseDTO erroResponseDTO = ErroResponseDTO.builder()
                  .timestamp(LocalDateTime.now())
                  .status(HttpStatus.UNAUTHORIZED.value())
                  .code(HttpStatus.UNAUTHORIZED.name())
                  .message("Credenciais inválidas! Usuário ou senha incorretos.")
                  .details("As credenciais fornecidas são inválidas. Verifique seu usuário e senha " +
                           "e tente novamente.")
                  .path(request.getRequestURI())
                  .build();

          return ResponseEntity.status(erroResponseDTO.getStatus())
                  .body(erroResponseDTO);
     }

}
