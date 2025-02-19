package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_client")
public class Client {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @EqualsAndHashCode.Include
     private Long id;

     private String clientId;

     private String clientSecret;

     @Column(name = "redirect_uri")
     private String redirectURI;

     private String scope;
}
