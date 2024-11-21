package com.petwellness.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class SwaggerAPIConfig {
    @Value("${petwellness.openapi.dev-url}")
    private String devUrl;

    @Value("${petwellness.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        // Definir el servidor de desarrollo
        Server devServer = new Server();
        devServer.setUrl(this.devUrl);
        devServer.setDescription("Development Server");

        // Definir el servidor de producción
        Server prodServer = new Server();
        prodServer.setUrl(this.prodUrl);
        prodServer.setDescription("Production Server");

        // Información de contacto
        Contact contact = new Contact();
        contact.setEmail("petwellness@gmail.com");
        contact.setName("Petwellness");
        contact.setUrl("https://www.petwellness.com"); // Poner link de la landing page

        License mitLicense = new License().name("MIT").url("https://opensource.org/licenses/MIT");

        // Configuración de seguridad JWT
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("JWT Authentication");

        Components components = new Components()
                .addSecuritySchemes("bearerAuth", securityScheme);

        // Requerimiento de seguridad para utilizar en las operaciones
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        // Información general de la API
        Info info = new Info()
                .title("API Petwellness veterinaria")
                .version("1.0")
                .contact(contact)
                .description("API Restful de veterinaria")
                .termsOfService("https://www.petwellness.com/terms")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
