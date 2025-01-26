package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_anexo")
public class Anexo {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @EqualsAndHashCode.Include
     private Long id;

     private String nome;
     private String tipo;
     private Long tamanho;
     private String url;

     @ManyToOne
     @JoinColumn(name = "tarefa_id", nullable = false)
     private Tarefa tarefa;
}
