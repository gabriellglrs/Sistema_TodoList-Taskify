package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_historico")
public class Historico {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @EqualsAndHashCode.Include
     private Long id;

     private String acao;
     private LocalDateTime dataModificacao;
     private String descricao;
}
