package org.sbaeker.quarkus.microservices.application;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 *
 * The main application class for the ProductService API.
 * Configures the application's base path and provides OpenAPI documentation.
 */

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Order Service API",
                description = "Receives orders from the Order Service and publishes the order as a message to the respective queue",
                version = "1.0",
                contact = @Contact(name = "@sbaeker")
        )
)
@ApplicationPath("/")
public class ProductServiceApplication extends Application {
}
