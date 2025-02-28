package com.dev.gabriellucas.taskify.DTO;

import com.dev.gabriellucas.taskify.enums.StatusLista;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ListaResponseDTO implements Serializable {
     private Long id;
     private Long usuarioId;
     private String titulo;
     private String descricao;
     private LocalDateTime dataCriacao;
     StatusLista status;
}
