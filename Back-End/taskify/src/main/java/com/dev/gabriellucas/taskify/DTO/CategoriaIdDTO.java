package com.dev.gabriellucas.taskify.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoriaIdDTO {
     private Long id;

     public CategoriaIdDTO(Long id) {
          this.id = id;
     }
}
