package org.sbaeker.quarkus.microservices.proxy;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 *
 * Interface representing a proxy for the ProductService.
 */

/**
 * Indicates that the annotated interface should be registered as a REST client.
 * This annotation is used in conjunction with MicroProfile Rest Client to identify
 * and configure the REST client implementation for the interface.
 *
 * <p>The {@code configKey} attribute can be used to specify a configuration key
 * that maps to the corresponding configuration properties. This key is used to
 * configure the client implementation at runtime, such as the base URL and any
 * additional properties required for the REST client.
 *
 * <p>Example usage:
 *
 * <pre>{@code
 * RegisterRestClient(configKey = "productService.proxy")
 * public interface ProductServiceProxy {
 *     // ...
 * }
 * }</pre>
 *
 * <p>In the above example, the {@code ProductServiceProxy} interface is annotated
 * with {@code @RegisterRestClient} and specifies the configuration key as
 * "productService.proxy". This allows configuring the implementation of the REST
 * client for the ProductServiceProxy interface using the corresponding configuration
 * properties.
 *
 * @see <a href="https://download.eclipse.org/microprofile/microprofile-rest-client-1.4.1/apidocs/org/eclipse/microprofile/rest/client/inject/RegisterRestClient.html">MicroProfile Rest Client - @RegisterRestClient</a>
 */
@RegisterRestClient(configKey = "productService.proxy")
@Path("/product-service")
public interface ProductServiceProxy {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    String handleIncomingOrders(String message);

}
