package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.HashSet;
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

     @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<Lista> listas = new HashSet<>();

     @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<Notificacao> notificacoes= new HashSet<>();
}
