package com.dev.gabriellucas.taskify.DTO;

import com.dev.gabriellucas.taskify.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioResponseDTO implements Serializable {
     private Long id;
     private String nome;
     private String email;
     private LocalDateTime dataCadastro;
     private Set<RoleResponseDTO> roles;
}
