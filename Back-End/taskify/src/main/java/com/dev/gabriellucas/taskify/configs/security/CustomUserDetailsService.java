package com.dev.gabriellucas.taskify.configs.security;

import com.dev.gabriellucas.taskify.entities.Usuario;
import com.dev.gabriellucas.taskify.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

     //private final UsuarioServiceImpl usuarioService;
     private final UsuarioRepository usuarioRepository;

     public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
          this.usuarioRepository = usuarioRepository;
     }

     @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

          return usuarioRepository.findByEmailWithRoles(email)
                  .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

     }
}
