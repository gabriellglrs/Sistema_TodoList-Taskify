package com.dev.gabriellucas.taskify.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientRequestDTO implements Serializable {

     @NotBlank(message = "O clientId é obrigatório")
     @Size(min = 3, max = 150, message = "O clientId deve ter entre 3 e 150 caracteres")
     private String clientId;

     @NotBlank(message = "O clientSecret é obrigatório")
     @Size(min = 3, message = "O clientSecret deve ter pelo menos 3 caracteres")
     private String clientSecret;

     @NotBlank(message = "O redirectURI é obrigatório")
     @Size(max = 200, message = "O redirectURI não pode ultrapassar 200 caracteres")
     @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "O redirectURI deve ser uma URL válida")
     private String redirectURI;

     private String scope;
}
