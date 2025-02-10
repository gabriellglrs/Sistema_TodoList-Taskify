package com.dev.gabriellucas.taskify.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
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
                  );
     }
}
