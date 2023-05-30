package org.sbaeker.quarkus.microservices.resource;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 *
 * The ProductServiceResource class is responsible for handling incoming orders and forwarding them to the appropriate queues.
 * It also receives recipes from the food-and-drink-recipes queue and provides them as server-sent events.
 * This resource is used by the product service.
 */

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;
import org.sbaeker.quarkus.microservices.classification.Classifier;

@Traced
@Path("/product-service")
public class ProductServiceResource {

    private static final Logger LOG = Logger.getLogger(ProductServiceResource.class);

    @Channel("barista-in")
    Emitter<String> baristaInEmitter;

    @Channel("kitchen-in")
    Emitter<String> kitchenInEmitter;

    @Inject
    Classifier classifier;

    @Inject
    @Channel("recipes")
    Publisher<String> newRecipes;

    /**
     * Handles incoming orders and forwards them to the barista-in or kitchen-in queue based on the order type.
     *
     * @param message the incoming order message in JSON format
     * @return the original order message
     */
    @POST
    @Tag(name = "Receives Orders as JSON and forwards them to the barista-in or kitchen-in queue")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String handleIncomingOrders(String message) {
        System.out.println("Message received: " + message);
        if(classifier.classifyOrder(message).equals("drink")){
            baristaInEmitter.send(message);
            System.out.println("Order sent to Barista");
        }

        if(classifier.classifyOrder(message).equals("food")){
            kitchenInEmitter.send(message);
            System.out.println("Order sent to Chef");
        }

        LOG.info("ProductServiceResource.class - Method: handleIncomingOrders(String)");
        return message;
    }

    /**
     * Receives recipes from the food-and-drink-recipes queue and provides them as server-sent events.
     *
     * @return the recipe as a server-sent event
     */

    @GET
    @Path("/recipes")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("text/plain")
    public Publisher<String> stream() {
        return newRecipes;
    }

}
