package com.dev.gabriellucas.taskify.configs.security;

import com.dev.gabriellucas.taskify.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {

     private final Usuario usuario;

     @Override
     public Collection<GrantedAuthority> getAuthorities() {
          return usuario.getRoles().stream()
                  .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                  .collect(Collectors.toList());
     }

     @Override
     public Object getCredentials() {
          return null;
     }

     @Override
     public Object getDetails() {
          return usuario;
     }

     @Override
     public Object getPrincipal() {
          return usuario;
     }

     @Override
     public boolean isAuthenticated() {
          return true;
     }

     @Override
     public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

     }

     @Override
     public String getName() {
          return usuario.getEmail();
     }
}
