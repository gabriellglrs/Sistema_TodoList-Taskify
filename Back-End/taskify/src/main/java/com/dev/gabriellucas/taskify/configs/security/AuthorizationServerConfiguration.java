package com.dev.gabriellucas.taskify.configs.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration { // Classe de configuração do servidor de autorização do OAuth2 (Authorization Server)

     @Bean
     @Order(1)
     public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception { // Método que retorna a cadeia de filtros de segurança do servidor de autorização
          OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

          http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                  .oidc(Customizer.withDefaults());

          http.oauth2ResourceServer(oauth2Rs -> oauth2Rs
                  .jwt(Customizer.withDefaults()));

          http.formLogin(Customizer.withDefaults());

          return http.build();
     }

     @Bean
     public TokenSettings tokenSettings() { // Método que retorna as configurações do token de acesso do cliente registrado
          return TokenSettings.builder()
                  .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED) // Formato do token de acesso do cliente registrado
                  .accessTokenTimeToLive(Duration.ofMinutes(60)) // Tempo de vida do token de acesso do cliente registrado
                  .refreshTokenTimeToLive(Duration.ofMinutes(90)) // Tempo de vida do token de atualização do token de acesso do cliente registrado (token de atualização é um token que pode ser usado para obter um novo token de acesso)
                  .build(); // Constrói as configurações do token de acesso do cliente registrado
     }

     @Bean
     public ClientSettings clientSettings() { // Método que retorna as configurações do cliente registrado
          return ClientSettings.builder()
                  .requireAuthorizationConsent(false) // Define se o consentimento do usuário é necessário para autorizar o cliente
                  .build(); // Constrói as configurações do cliente registrado
     }

     @Bean
     public PasswordEncoder passwordEncoder() { // Método que retorna o codificador de senha BCrypt, com força de 10 iterações para criptografar senhas de usuários
          return new BCryptPasswordEncoder(10);
     }

     @Bean
     public JWKSource<SecurityContext> jwkSource() throws Exception { // Método que retorna a fonte de chaves JWK (JSON Web Key)
          RSAKey rsaKey = gerarChaveRSA(); // Gera uma chave RSA
          JWKSet jwkSet = new JWKSet(rsaKey);
          return new ImmutableJWKSet<>(jwkSet);
     }

     // Método que gera uma chave RSA com 2048 bits de tamanho e retorna um objeto RSAKey com a chave pública, chave privada e um ID de chave aleatório
     private RSAKey gerarChaveRSA() throws Exception {
          KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
          keyPairGenerator.initialize(2048);
          KeyPair keyPair = keyPairGenerator.generateKeyPair();

          RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
          RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

          return new RSAKey.Builder(publicKey)
                  .privateKey(privateKey)
                  .keyID(UUID.randomUUID().toString())
                  .build();
     }

     @Bean
     public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) { // Método que retorna o decodificador de token JWT
          return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
     }

     @Bean
     public AuthorizationServerSettings authorizationServerSettings() { // Método que retorna as configurações do servidor de autorização
          return AuthorizationServerSettings.builder()
                  .tokenEndpoint("/oauth2/token") // (URI para onde o cliente envia uma solicitação para obter um token de acesso)
                  .tokenIntrospectionEndpoint("/oauth2/introspect") // para consultar status de um token de acesso
                  .tokenRevocationEndpoint("/oauth2/revoke") // para revogar um token de acesso ou um token de atualização
                  .authorizationEndpoint("/oauth2/authorize") // (URI para onde o cliente redireciona o usuário para obter autorização)
                  .oidcUserInfoEndpoint("/userinfo") // (URI para onde o cliente envia uma solicitação para obter informações do usuário)
                  .jwkSetEndpoint("/oauth2/keys") // (URI para onde o cliente envia uma solicitação para obter as chaves JWK )
                  .oidcLogoutEndpoint("/logout") // (URI para onde o cliente redireciona o usuário após o logout)
                  .build(); // Constrói as configurações do servidor de autorização
     }
}
