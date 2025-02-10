package com.dev.gabriellucas.taskify.DTO;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AnexoResponseDTO implements Serializable {

    private Long id;

    private String nome;
    private String tipo;
    private Long tamanho;
    private String url;
}
