//package com.dev.gabriellucas.taskify.configs.security;
//
//import com.dev.gabriellucas.taskify.entities.Usuario;
//import com.dev.gabriellucas.taskify.services.impl.UsuarioServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//     private final UsuarioServiceImpl usuarioService;
//     private final PasswordEncoder encoder;
//
//     @Override
//     public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//          String email = authentication.getName();
//          String senhaDigitada = authentication.getCredentials().toString();
//
//          Usuario usuarioEncontrado = usuarioService.findByEmail(email);
//
//          if(usuarioEncontrado == null){
//               throw getErroUsuarioNaoEncontrado();
//          }
//
//          String senhaCriptografada = usuarioEncontrado.getSenha();
//
//          boolean senhamBatem = encoder.matches(senhaDigitada, senhaCriptografada);
//
//          if(senhamBatem){
//               return new CustomAuthentication(usuarioEncontrado);
//          }
//
//          throw getErroUsuarioNaoEncontrado();
//     }
//
//     private UsernameNotFoundException getErroUsuarioNaoEncontrado() {
//          return new UsernameNotFoundException("E-mail ou senha inválidos. Tente novamente.");
//     }
//
//     @Override
//     public boolean supports(Class<?> authentication) {
//          return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
//     }
//}
