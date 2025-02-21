package com.dev.gabriellucas.taskify.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSocialSuccessHandler successHandler, JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter) throws Exception {
          return http
                  .csrf(AbstractHttpConfigurer::disable) // Desativa proteção CSRF (para APIs REST)
                  .formLogin(Customizer.withDefaults()) // Define autenticação via formulário
                  .httpBasic(Customizer.withDefaults()) // Define autenticação básica

                  .oauth2Login(oauth2 -> oauth2
                          .successHandler(successHandler)
                  )
                  .oauth2ResourceServer(oauth2 -> oauth2 //
                          .jwt(Customizer.withDefaults()) // Define autenticação via JWT
                  )
                  .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                          .requestMatchers("/auth/login", "/auth/register").permitAll() // Permite acesso ao login e cadastro
                          .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permite acesso ao Swagger
                          .requestMatchers("/api/usuarios/register").permitAll() // Permite acesso ao Swagger
                          .anyRequest().authenticated() // Exige autenticação para outras rotas
                  )
                  .addFilterAfter(jwtCustomAuthenticationFilter, BearerTokenAuthenticationFilter.class)
                  .build();
     }

     @Bean
     public JwtAuthenticationConverter jwtAuthenticationConverter() { // Converte o token JWT em autenticação
          var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
          authoritiesConverter.setAuthorityPrefix("");
          var converter = new JwtAuthenticationConverter();
          converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

          return converter;
     }


     // Define o prefixo para as roles
     @Bean
     public GrantedAuthorityDefaults grantedAuthorityDefaults() {
          return new GrantedAuthorityDefaults(""); // Remove o prefixo ROLE_
     }
}
