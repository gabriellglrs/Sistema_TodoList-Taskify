package com.dev.gabriellucas.taskify.configs.security;

import com.dev.gabriellucas.taskify.entities.Client;
import com.dev.gabriellucas.taskify.services.impl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomRegisteredClientsRepository implements RegisteredClientRepository {

     private final ClientServiceImpl clientService;
     private final TokenSettings tokenSettings;
     private final ClientSettings clientSettings;

     @Override
     public void save(RegisteredClient registeredClient) { // Método que salva um cliente registrado

     }

     @Override
     public RegisteredClient findById(String id) { // Método que retorna um cliente registrado com base no ID
          return null;
     }

     @Override
     public RegisteredClient findByClientId(String clientId) { // Método que retorna um cliente registrado com base no ID do cliente
          Client client = clientService.BuscarPorId(clientId);
          if (client == null) {
               return null;
          }
          return RegisteredClient
                  .withId(client.getId().toString()) // ID do cliente registrado no banco de dados
                  .clientId(client.getClientId()) // ID do cliente que será usado para autenticação
                  .clientSecret(client.getClientSecret()) // Senha do cliente que será usada para autenticação
                  .redirectUri(client.getRedirectURI()) // URI para onde o provedor de autorização redirecionará o usuário após a conclusão da autorização
                  .scope(client.getScope()) // Escopos que o cliente está autorizado a solicitar
                  .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC) // Método de autenticação do cliente
                  .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // AUTHORIZATION_CODE é um tipo de concessão de autorização que é usado para obter um token de acesso após a autorização do usuário
                  .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) // CLIENT_CREDENTIALS é um tipo de concessão de autorização que é usado para obter um token de acesso em nome do cliente
                  .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) // REFRESH_TOKEN é um tipo de concessão de autorização que é usado para obter um novo token de acesso usando um token de atualização
                  .tokenSettings(tokenSettings) // Configurações do token de acesso do cliente registrado, classe AuthorizationServerConfiguration
                  .clientSettings(clientSettings) // Configurações do cliente registrado, classe AuthorizationServerConfiguration
                  .build(); // Constrói o cliente registrado
     }
}
