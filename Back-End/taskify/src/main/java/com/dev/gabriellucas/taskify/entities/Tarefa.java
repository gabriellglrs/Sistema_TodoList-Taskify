package com.dev.gabriellucas.taskify.entities;

import com.dev.gabriellucas.taskify.enums.PrioridadeTarefa;
import com.dev.gabriellucas.taskify.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "tb_tarefa")
public class Tarefa {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @EqualsAndHashCode.Include
     private Long id;

     private String titulo;
     private String descricao;
     private LocalDateTime dataCriacao;
     private LocalDateTime dataVencimento;

     @Enumerated(EnumType.STRING)
     private PrioridadeTarefa prioridade;

     @Enumerated(EnumType.STRING)
     private StatusTarefa status;

     @ManyToOne
     @JoinColumn(name = "lista_id")
     private Lista lista;

     @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<Comentario> comentarios = new HashSet<>();

     @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<Anexo> anexos = new HashSet<>();

     @OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<Historico> historicos = new HashSet<>();

     @ManyToMany
     @JoinTable(name = "tb_tarefa_categoria",
             joinColumns = @JoinColumn(name = "tarefa_id"),
             inverseJoinColumns = @JoinColumn(name = "categoria_id"))
     private Set<Categoria> categorias = new HashSet<>();

     @ManyToMany
     @JoinTable(name = "tb_tarefa_etiqueta",
             joinColumns = @JoinColumn(name = "tarefa_id"),
             inverseJoinColumns = @JoinColumn(name = "etiqueta_id"))
     private Set<Etiqueta> etiquetas = new HashSet<>();
}
