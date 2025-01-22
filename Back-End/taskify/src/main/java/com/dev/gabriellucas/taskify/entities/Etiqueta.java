package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tb_etiqueta")
public class Etiqueta {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String nome;
     private String cor;

     @ManyToMany(mappedBy = "etiquetas")
     private Set<Tarefa> tarefas = new HashSet<>();
}
