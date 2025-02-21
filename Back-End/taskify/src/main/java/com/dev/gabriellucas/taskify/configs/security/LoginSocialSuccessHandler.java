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

     @Override
     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws ServletException, IOException {

          // Converte a autenticação para um objeto específico do OAuth2
          OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;

          // Obtém o usuário autenticado via OAuth2
          OAuth2User oauth2User = authenticationToken.getPrincipal();

          // Obtém o e-mail do usuário autenticado via OAuth2
          String email = oauth2User.getAttribute("email");

          // Obtém o nome do usuário autenticado via OAuth2
          String nome = oauth2User.getAttribute("name");

          // Busca o usuário no banco de dados pelo e-mail
          Usuario usuario = usuarioService.findByEmail(email);

          // Se o usuário não existir, cadastra um novo usuário na base de dados
          if (usuario == null) {
               usuario = new Usuario();
               usuario.setEmail(email);
               usuario.setNome(nome);
               final String SENHA_PADRAO = "321"; // Senha padrão para usuários autenticados via OAuth2
               usuario.setSenha(passwordEncoder.encode(SENHA_PADRAO)); // Codifica a senha
               Role role = roleRepository.findById(1L)
                       .orElseThrow(() -> new RuntimeException("Role com ID 1 não encontrado"));
               usuario.setRoles(Set.of(role)); // Define o perfil do usuário
               usuario.setDataCadastro(LocalDateTime.now()); // Define a data de criação

               usuarioService.saveEntity(usuario);
          }

          // Cria um objeto de autenticação personalizada baseado no usuário encontrado
          CustomAuthentication customAuthentication = new CustomAuthentication(usuario);

          // Define a autenticação personalizada no contexto de segurança do Spring Security
          SecurityContextHolder.getContext().setAuthentication(customAuthentication);

          // Define a URL padrão de redirecionamento
          setDefaultTargetUrl("/home");

          // Chama o comportamento padrão de sucesso APENAS UMA VEZ, após processar tudo
          super.onAuthenticationSuccess(request, response, customAuthentication);
     }

}
