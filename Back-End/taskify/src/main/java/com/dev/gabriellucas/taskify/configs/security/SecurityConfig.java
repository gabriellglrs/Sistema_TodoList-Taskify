package com.dev.gabriellucas.taskify.configs.security;

import com.dev.gabriellucas.taskify.repositories.RoleRepository;
import com.dev.gabriellucas.taskify.services.impl.UsuarioServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler successHandler) throws Exception {
          return http
                  .csrf(AbstractHttpConfigurer::disable) // Desativa proteção CSRF (para APIs REST)
                  .formLogin(Customizer.withDefaults()) // Define autenticação via formulário
                  .httpBasic(Customizer.withDefaults()) // Define autenticação básica
                  .logout(logout -> logout
                          .logoutUrl("/auth/logout") // Define a URL de logout
                          .logoutSuccessHandler((request, response, authentication) -> {
                               response.sendRedirect("/api/usuarios/1"); // Redireciona para a URL customizada após logout
                          })
                          .permitAll()
                  )
                  .oauth2Login(oauth2 -> oauth2
                          .successHandler(successHandler)
                  )
                  .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                          .requestMatchers("/auth/login", "/auth/register").permitAll() // Permite acesso ao login e cadastro
                          .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permite acesso ao Swagger
                          .requestMatchers("/api/usuarios/register").permitAll() // Permite acesso ao Swagger
                          .anyRequest().authenticated() // Exige autenticação para outras rotas
                  )
                  .build();
     }

     @Bean
     public PasswordEncoder passwordEncoder() {
          return new BCryptPasswordEncoder(10);
     }

     @Bean
     public UserDetailsService userDetailsService(UsuarioServiceImpl usuarioService) {
          return new CustomUserDetailsService(usuarioService);
     }

     @Bean
     public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
          DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
          authProvider.setUserDetailsService(userDetailsService);
          authProvider.setPasswordEncoder(passwordEncoder); // Compara senhas criptografadas corretamente
          return new ProviderManager(authProvider);
     }

     // Define o prefixo para as roles
     @Bean
     public GrantedAuthorityDefaults grantedAuthorityDefaults() {
          return new GrantedAuthorityDefaults(""); // Remove o prefixo ROLE_
     }

}
