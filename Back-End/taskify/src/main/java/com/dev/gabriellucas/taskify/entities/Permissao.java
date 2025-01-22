package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_permissao")
public class Permissao {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
}
