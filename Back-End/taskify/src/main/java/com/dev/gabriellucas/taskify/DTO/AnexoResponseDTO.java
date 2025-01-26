package com.dev.gabriellucas.taskify.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AnexoResponseDTO {

    private Long id;

    private String nome;
    private String tipo;
    private Long tamanho;
    private String url;
}
