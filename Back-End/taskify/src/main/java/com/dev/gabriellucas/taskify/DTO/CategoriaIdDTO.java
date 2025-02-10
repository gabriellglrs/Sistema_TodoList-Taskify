package com.dev.gabriellucas.taskify.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaIdDTO implements Serializable {
     private Long id;

     public CategoriaIdDTO(Long id) {
          this.id = id;
     }
}
