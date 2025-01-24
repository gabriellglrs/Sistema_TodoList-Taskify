package com.dev.gabriellucas.taskify.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioRequestPatchDTO {
     private Long id; // ID pode ser nulo em operações de PATCH, se necessário

     @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
     @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "Nome deve conter apenas letras e espaços")
     private String nome;

     @Email(message = "Email deve ser válido")
     @Size(min = 8, max = 150, message = "Email deve ter entre 8 e 150 caracteres")
     private String email;

     @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
     @Pattern(regexp = "(?=.*[A-Z]).+", message = "Senha deve conter pelo menos uma letra maiúscula")
     @Pattern(regexp = "(?=.*[0-9]).+", message = "Senha deve conter pelo menos um número")
     @Pattern(regexp = "(?=.*[!@#$%^&*()_+\\-=\\[\\] {};':\"\\\\|,.<>\\/?]).+", message = "Senha deve conter pelo menos um caractere especial")
     private String senha;

     private LocalDateTime dataCadastro; // Pode ser nulo para evitar alterações desnecessárias

}
