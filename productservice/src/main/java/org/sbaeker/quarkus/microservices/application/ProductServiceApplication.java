package org.sbaeker.quarkus.microservices.application;



import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

/**
 * The ProductServiceApplication class represents the main application class for the ProductService.
 * It extends the JAX-RS Application class and is responsible for configuring the application and defining the base path.
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * ProductServiceApplication application = new ProductServiceApplication();
 * }</pre>
 *
 * <p>The ProductServiceApplication class is annotated with {@code @OpenAPIDefinition} to provide metadata for the OpenAPI documentation.
 * The {@code @Info} annotation within {@code @OpenAPIDefinition} specifies details such as the title, description, version, and contact information.</p>
 *
 * <p>The class is also annotated with {@code @ApplicationPath} to define the base path for the application.</p>
 *
 * @see OpenAPIDefinition
 * @see Info
 * @see Contact
 * @see ApplicationPath
 * @see Application
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 *
 */
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
