package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_anexo")
public class Anexo {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String nome;
     private String tipo;
     private Long tamanho;
     private String url;
}
