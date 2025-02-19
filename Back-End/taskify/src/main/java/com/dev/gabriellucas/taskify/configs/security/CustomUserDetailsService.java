package com.dev.gabriellucas.taskify.configs.security;

import com.dev.gabriellucas.taskify.entities.Usuario;
import com.dev.gabriellucas.taskify.services.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

     private final UsuarioServiceImpl usuarioService;

     @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
          Usuario usuario = usuarioService.findByEmail(email);
          return usuario;
     }
}