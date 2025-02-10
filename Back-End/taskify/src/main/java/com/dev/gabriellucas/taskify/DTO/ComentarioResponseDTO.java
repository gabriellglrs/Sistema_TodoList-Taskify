package com.dev.gabriellucas.taskify.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ComentarioResponseDTO implements Serializable {

    private Long id;

    private String texto;

    private LocalDateTime dataCriacao;
}
