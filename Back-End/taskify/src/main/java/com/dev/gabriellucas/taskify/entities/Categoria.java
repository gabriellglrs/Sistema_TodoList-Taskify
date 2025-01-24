package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_categoria")
public class Categoria {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @EqualsAndHashCode.Include
     private Long id;

     private String nome;
     private String descricao;
     private String cor;

     @ManyToMany(mappedBy = "categorias")
     private Set<Tarefa> tarefas = new HashSet<>();
}
