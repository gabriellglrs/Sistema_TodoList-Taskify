package com.dev.gabriellucas.taskify.DTO;

import com.dev.gabriellucas.taskify.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class UsuarioRequestDTO implements Serializable {
     private Long id;

     @NotEmpty(message = "Nome não pode ser vazio")
     @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
     @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "Nome deve conter apenas letras e espaços")
     private String nome;

     @NotEmpty(message = "Email não pode ser vazio")
     @Email(message = "Email deve ser válido")
     @Size(max = 150, min = 8, message = "Email deve ter entre 8 e 150 caracteres")
     private String email;

     @NotEmpty(message = "Senha não pode ser vazia")
     @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
     @Pattern(regexp = "(?=.*[A-Z]).+", message = "Senha deve conter pelo menos uma letra maiúscula")
     @Pattern(regexp = "(?=.*[0-9]).+", message = "Senha deve conter pelo menos um número")
     @Pattern(regexp = "(?=.*[!@#$%^&*()_+\\-=\\[\\] {};':\"\\\\|,.<>\\/?]).+", message = "Senha deve conter pelo menos um caractere especial")
     private String senha;

     private Set<RoleResponseDTO> roles;
}
