package com.dev.gabriellucas.taskify.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class CategoriaResponseDTO implements Serializable {
     private Long id;
     private String nome;
     private String descricao;
     private String cor;
}
