package org.sbaeker.quarkus.microservices.proxy;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.micrometer.core.annotation.Timed;

/**
 * The ProductServiceProxy interface represents a proxy for the Product Service.
 * It is used to handle incoming orders and communicate with the Product Service.
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * ProductServiceProxy productServiceProxy = RestClientBuilder.newBuilder()
 *                                 .baseUri(URI.create("http://product-service.com"))
 *                                 .build(ProductServiceProxy.class);
 * String response = productServiceProxy.handleIncomingOrders(orderJson);
 * }</pre>
 *
 * <p>The ProductServiceProxy interface is annotated with {@code @RegisterRestClient} to indicate that it is a REST client.
 * The {@code configKey} attribute specifies the configuration key for the RestClientBuilder to retrieve the base URL of the Product Service.</p>
 *
 * <p>The ProductServiceProxy interface is also annotated with {@code @Path} to specify the base path for the Product Service API.</p>
 *
 * <p>The handleIncomingOrders() method is annotated with {@code @POST} to indicate that it handles HTTP POST requests.
 * It is annotated with {@code @Produces} and {@code @Consumes} to specify the media type of the request and response payloads.</p>
 *
 * @see RegisterRestClient
 * @see Path
 * @see POST
 * @see Produces
 * @see Consumes
 * @see MediaType
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0
 */
@RegisterRestClient(configKey = "productService.proxy")
@Path("/product-service")
public interface ProductServiceProxy {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed("order.service.time.to.send.order.to.product.service")
    String handleIncomingOrders(String message);

}
