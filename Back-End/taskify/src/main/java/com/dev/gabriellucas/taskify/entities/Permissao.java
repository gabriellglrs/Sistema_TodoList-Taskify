package com.dev.gabriellucas.taskify.entities;

import com.dev.gabriellucas.taskify.enums.NivelAcesso;
import com.dev.gabriellucas.taskify.enums.TipoPermissao;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_permissao")
public class Permissao {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     @Enumerated(EnumType.STRING)
     private TipoPermissao tipo;
     @Enumerated(EnumType.STRING)
     private NivelAcesso nivel;

     @OneToOne
     @JoinColumn(name = "lista_id")
     private Lista lista;
}
