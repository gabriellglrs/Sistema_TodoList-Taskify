package com.dev.gabriellucas.taskify.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientResponseDTO implements Serializable {
     private Long id;
     private String clientId;
     private String clientSecret;
     private String redirectURI;
     private String scope;
}
