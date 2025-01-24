package com.dev.gabriellucas.taskify.entities;

import com.dev.gabriellucas.taskify.enums.StatusLista;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_lista")
public class Lista {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @EqualsAndHashCode.Include
     private Long id;

     private String titulo;
     private String descricao;
     private LocalDateTime dataCriacao;

     @Enumerated(EnumType.STRING)
     StatusLista status;

     @ManyToOne
     @JoinColumn(name = "usuario_id")
     private Usuario usuario;

     @OneToMany(mappedBy = "lista")
     private Set<Tarefa> tarefas = new HashSet<>();

     @OneToOne(mappedBy = "lista")
     private Permissao permissao;

}
