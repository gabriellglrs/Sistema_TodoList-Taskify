package com.dev.gabriellucas.taskify.DTO;

import com.dev.gabriellucas.taskify.enums.StatusNotificacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class NotificacaoResponseDTO implements Serializable {
     private Long id;
     private String titulo;
     private String mensagem;
     private LocalDateTime dataEnvio;
     private StatusNotificacao status;
}
