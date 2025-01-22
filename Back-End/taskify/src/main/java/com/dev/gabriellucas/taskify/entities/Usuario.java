package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tb_usuario")
public class Usuario {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String nome;
     private String email;
     private String senha;
     private LocalDateTime dataCadastro;

     @OneToMany(mappedBy = "usuario")
     private Set<Lista> listas = new HashSet<>();

     @OneToMany
     @JoinColumn(name = "usuario_id")
     private Set<Notificacao> notificacoes= new HashSet<>();

}
