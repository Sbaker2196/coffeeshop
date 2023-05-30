package org.sbaeker.quarkus.microservices.application;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 */

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

/**
 * Application class for the Order Service API.
 * Configures the application's base path and provides OpenAPI documentation.
 */

@OpenAPIDefinition(
        info = @Info(
                title = "Order Service API",
                description = "Allows customers to place orders",
                version = "1.0",
                contact = @Contact(name = "@sbaeker")
        )
)
@ApplicationPath("/")
@ApplicationScoped
public class OrderServiceApplication extends Application {
}
