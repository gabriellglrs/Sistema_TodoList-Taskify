package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_comentario")
public class Comentario {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @EqualsAndHashCode.Include
     private Long id;

     private String texto;
     private LocalDateTime dataCriacao;

     @ManyToOne
     @JoinColumn(name = "tarefa_id", nullable = false)
     private Tarefa tarefa;
}
