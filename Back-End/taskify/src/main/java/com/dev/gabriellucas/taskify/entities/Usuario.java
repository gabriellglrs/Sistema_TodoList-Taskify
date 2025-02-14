package com.dev.gabriellucas.taskify.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_usuario")
public class Usuario implements UserDetails {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @EqualsAndHashCode.Include
     private Long id;

     private String nome;

     @Column(unique = true)
     private String email;

     private String senha;

     private LocalDateTime dataCadastro;

     @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<Lista> listas = new HashSet<>();

     @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
     private Set<Notificacao> notificacoes= new HashSet<>();

     @ManyToMany
     @JoinTable(
             name = "tb_usuario_role",
             joinColumns = @JoinColumn(name = "usuario_id"),
             inverseJoinColumns = @JoinColumn(name = "role_id")
     )
     private Set<Role> roles = new HashSet<>();

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
          return roles;
     }

     @Override
     public String getPassword() {
          return senha;
     }

     @Override
     public String getUsername() {
          return email;
     }

     @Override
     public boolean isAccountNonExpired() {
          return true;
     }

     @Override
     public boolean isAccountNonLocked() {
          return true;
     }

     @Override
     public boolean isCredentialsNonExpired() {
          return true;
     }

     @Override
     public boolean isEnabled() {
          return true;
     }
}
