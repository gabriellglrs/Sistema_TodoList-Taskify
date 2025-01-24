package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_usuario")
public class Usuario {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @EqualsAndHashCode.Include
     private Long id;
     private String nome;
     private String email;
     private String senha;
     private LocalDateTime dataCadastro;

     @OneToMany(mappedBy = "usuario")
     private Set<Lista> listas = new HashSet<>();

     @OneToMany(mappedBy = "usuario")
     private Set<Notificacao> notificacoes= new HashSet<>();

}
