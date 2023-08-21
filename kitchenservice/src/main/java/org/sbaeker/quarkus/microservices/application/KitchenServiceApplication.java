package org.sbaeker.quarkus.microservices.application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 25.07.2023
 */

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Application class for the Kitchen Service API
 * Configures the Applications base path and provides OpenAPI documentation
 */

@OpenAPIDefinition(
    info = @Info(title = "Kitchen Service API",
                 description =
                     "Enables customers to retrieve the recipes from the DB",
                 version = "1.0", contact = @Contact(name = "@sbaeker")))
@ApplicationPath("/")
@ApplicationScoped
public class KitchenServiceApplication extends Application {}
