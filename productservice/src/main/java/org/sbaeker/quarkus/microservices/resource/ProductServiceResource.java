package org.sbaeker.quarkus.microservices.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;
import org.sbaeker.quarkus.microservices.classification.Classifier;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
/**
 * The ProductServiceResource class is a RESTful resource that handles incoming orders and provides
 * a stream of recipes. It receives orders as JSON and forwards them to the appropriate queues based
 * on their classification. It also provides a server-sent event (SSE) stream of recipes.
 *
 * <p>Usage example:
 *
 * <pre>{@code
 * ProductServiceResource productServiceResource = new ProductServiceResource();
 * String response = productServiceResource.handleIncomingOrders(orderMessage);
 * Publisher<String> recipeStream = productServiceResource.stream();
 * }</pre>
 *
 * <p>The ProductServiceResource class is annotated with {@code @Path} to specify the base path for
 * the resource.
 *
 * <p>The resource has the following endpoints:
 *
 * <ul>
 *   <li>{@code POST /product-service}: Receives orders as JSON and forwards them to the appropriate
 *       queues.
 *   <li>{@code GET /product-service/recipes}: Provides a server-sent event (SSE) stream of recipes.
 * </ul>
 *
 * <p>The resource uses CDI (Contexts and Dependency Injection) to inject the necessary
 * dependencies, such as emitters and the classifier.
 *
 * <p>Note: This class assumes that there are predefined channels named "barista-in" and
 * "kitchen-in" for message forwarding.
 *
 * @see Path
 * @see Inject
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
@Path("/product-service")
public class ProductServiceResource {

  private static final Logger LOG = Logger.getLogger(ProductServiceResource.class);

  /** The emitter for sending messages to the "barista-in" channel. */
  @Channel("barista-in")
  Emitter<String> baristaInEmitter;

  /** The emitter for sending messages to the "kitchen-in" channel. */
  @Channel("kitchen-in")
  Emitter<String> kitchenInEmitter;

  /** The classifier for classifying orders. */
  @Inject Classifier classifier;

  /** The publisher for the "recipes" channel, providing a stream of recipes. */
  @Inject
  @Channel("recipes")
  Publisher<String> newRecipes;
  
  private final MeterRegistry registry;

  ProductServiceResource(MeterRegistry registry) {
    this.registry = registry;
  }

  /**
   * Handles incoming orders by forwarding them to the appropriate queues based on their
   * classification.
   *
   * @param message the incoming order message in JSON format
   * @return the original order message
   */
  @POST
  @Tag(name = "Receives Orders as JSON and forwards them to the barista-in or kitchen-in queue")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Timed("product.service.time.to.handle.incoming.orders")
  public String handleIncomingOrders(String message) {
    System.out.println("Message received: " + message);

    if (classifier.classifyOrder(message).equals("drink")) {
      baristaInEmitter.send(message);
      registry.counter("product.service.amount.of.orders.for.barista").increment();
      System.out.println("Order sent to Barista");
    }

    if (classifier.classifyOrder(message).equals("food")) {
      kitchenInEmitter.send(message);
      registry.counter("product.service.amount.of.orders.for.kitchen").increment();
      System.out.println("Order sent to Chef");
    }

    LOG.info("ProductServiceResource.class - Method: handleIncomingOrders(String)");
    return message;
  }

  /**
   * Provides a server-sent event (SSE) stream of recipes.
   *
   * @return the publisher for the recipe stream
   */
  @GET
  @Path("recipes")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  @SseElementType("text/plain")
  public Publisher<String> stream() {
    registry.counter("product.service.amount.of.recipes.sent").increment();
    return newRecipes;
  }
}
