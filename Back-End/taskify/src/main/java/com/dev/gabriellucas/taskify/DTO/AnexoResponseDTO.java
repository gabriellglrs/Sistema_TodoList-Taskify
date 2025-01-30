package com.dev.gabriellucas.taskify.DTO;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AnexoResponseDTO {

    private Long id;

    private String nome;
    private String tipo;
    private Long tamanho;
    private String url;
}
