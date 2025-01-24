package com.dev.gabriellucas.taskify.entities;

import com.dev.gabriellucas.taskify.enums.StatusNotificacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_notificacao")
public class Notificacao {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @EqualsAndHashCode.Include
     private Long id;

     private String titulo;
     private String mensagem;
     private LocalDateTime dataEnvio;

     @Enumerated(EnumType.STRING)
     private StatusNotificacao status;

     @ManyToOne
     @JoinColumn(name = "usuario_id")
     private Usuario usuario;

}
