package com.dev.gabriellucas.taskify.configs.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
     @Bean
     public OpenAPI customOpenAPI() {
          return new OpenAPI()
                  .info(new Info()
                          .title("Sistema TodoList - Taskify")
                          .version("1.0")
                          .description("\"A API **Taskify** permite a gestão eficiente de tarefas, proporcionando um sistema de lista de afazeres simples e intuitivo. Com funcionalidades como criação, edição, exclusão e visualização de tarefas, a API visa ajudar os usuários a se organizarem de maneira eficaz, melhorando a produtividade no dia a dia. Esta documentação fornece detalhes sobre todos os endpoints da API, como utilizá-los e as melhores práticas para integrar a solução em sistemas externos.")
                          .contact(new Contact()
                                  .name("Suporte da API")
                                  .email("gabriellglrs@gmail.com")
                                  .url("https://github.com/gabriellglrs")
                          )
                          .license(new License()
                                  .name("Licença Apache 2.0")
                                  .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                  ).addSecurityItem(new SecurityRequirement().addList("BearerAuth")) // Adiciona o esquema de segurança BearerAuth a todos os endpoints da API para autenticação via token JWT
                  .components(new Components() // Adiciona os componentes de segurança da API (esquemas de segurança) para autenticação via token JWT (BearerAuth) e OAuth2
                          .addSecuritySchemes("BearerAuth", new SecurityScheme() // Adiciona o esquema de segurança BearerAuth para autenticação via token JWT
                                  .name("BearerAuth") // Nome do esquema de segurança
                                  .type(SecurityScheme.Type.HTTP) // Tipo de esquema de segurança
                                  .scheme("bearer") // Esquema de segurança
                                  .bearerFormat("JWT") // Formato do token JWT
                          ));
     }
}
