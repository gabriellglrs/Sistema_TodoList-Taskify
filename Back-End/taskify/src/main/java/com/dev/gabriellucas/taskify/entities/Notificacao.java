package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_notificacao")
public class Notificacao {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String titulo;
     private String mensagem;
     private LocalDateTime dataEnvio;
}
