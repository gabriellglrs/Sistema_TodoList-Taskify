package com.dev.gabriellucas.taskify.configs.security;

import com.dev.gabriellucas.taskify.entities.Usuario;
import com.dev.gabriellucas.taskify.services.impl.UsuarioServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {

     private final UsuarioServiceImpl usuarioService;

     @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          if (authentication != null && authentication instanceof JwtAuthenticationToken) {
             String email = authentication.getName();
               Usuario usuario = usuarioService.findByEmail(email);
               if (usuario != null) {
                    authentication = new CustomAuthentication(usuario);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
               }
          }
          filterChain.doFilter(request, response);
     }
}
