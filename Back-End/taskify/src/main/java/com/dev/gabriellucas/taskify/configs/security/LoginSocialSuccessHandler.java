package com.dev.gabriellucas.taskify.configs.security;

import com.dev.gabriellucas.taskify.entities.Role;
import com.dev.gabriellucas.taskify.entities.Usuario;
import com.dev.gabriellucas.taskify.repositories.RoleRepository;
import com.dev.gabriellucas.taskify.services.impl.UsuarioServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

     private final UsuarioServiceImpl usuarioService;
     private final RoleRepository roleRepository;
     private final PasswordEncoder passwordEncoder;
     private final UserDetailsService userDetailsService;

     @Override
     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws ServletException, IOException {

          if (response.isCommitted()) {
               log.debug("A resposta já foi enviada. Não é possível redirecionar.");
               return;
          }

          OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
          OAuth2User oauth2User = authenticationToken.getPrincipal();

          String email = oauth2User.getAttribute("email");
          String nome = oauth2User.getAttribute("name");

          Usuario usuario = usuarioService.findByEmail(email);

          UserDetails userDetails = userDetailsService.loadUserByUsername(email);

          if (usuario == null) {
               userDetails = processOAuthUser(email, nome);
          }

          UsernamePasswordAuthenticationToken newAuth =
                  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          newAuth.setDetails(authentication.getDetails());

          SecurityContextHolder.getContext().setAuthentication(newAuth);

          setDefaultTargetUrl("/api/usuarios/1");
          super.onAuthenticationSuccess(request, response, newAuth);
     }

     private UserDetails processOAuthUser(String email, String nome) {
          Usuario usuario = usuarioService.findByEmail(email);
          if (usuario == null) {
               log.debug("Creating new user for email: {}", email);
               usuario = new Usuario();
               usuario.setEmail(email);
               usuario.setNome(nome);
               final String SENHA = "123";
               usuario.setSenha(passwordEncoder.encode(SENHA));
               Role role = roleRepository.findById(1L)
                       .orElseThrow(() -> new RuntimeException("Role com ID 1 não encontrado"));
               usuario.setRoles(Set.of(role));
               usuario.setDataCadastro(LocalDateTime.now());

               usuarioService.saveEntity(usuario);
          }
          return usuario;
     }
}
