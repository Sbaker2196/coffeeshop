package org.sbaeker.quarkus.microservices.resource;

import com.google.gson.Gson;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.sbaeker.quarkus.microservices.dao.KitchenDAOImpl;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.model.Recipe;
import io.micrometer.core.annotation.Timed;
/**
 * The KitchenServiceResource class is responsible for receiving orders and processing them in the kitchen service.
 * It retrieves the order information, retrieves the corresponding recipe from the database, and returns the recipe information.
 * This resource class is specifically designed for the kitchen service.
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * KitchenServiceResource kitchenResource = new KitchenServiceResource();
 * String orderMessage = "{\"name\": \"Cappuccino\", \"price\": \"3.99\"}";
 * String recipe = kitchenResource.receiveOrder(orderMessage);
 * System.out.println(recipe);
 * }</pre>
 *
 * <p>The KitchenServiceResource class is an application-scoped CDI bean that handles incoming messages from the "kitchen-in" channel
 * and produces outgoing messages to the "recipes" channel.</p>
 *
 * <p>The receiveOrder() method is annotated with {@code @Incoming} to indicate that it is a consumer of messages from the "kitchen-in" channel.
 * It is also annotated with {@code @Outgoing} to indicate that it produces messages to the "recipes" channel.
 * The {@code @Merge} and {@code @Broadcast} annotations specify the merging and broadcasting behavior of the messages, respectively.</p>
 *
 * <p>The receiveOrder() method receives a JSON message as input, parses it into an Order object using Gson,
 * retrieves the corresponding recipe from the database using the KitchenDAOImpl class, and returns the recipe information as a string.</p>
 *
 * <p>The toString() method of the Recipe class is called to obtain the string representation of the recipe.</p>
 *
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
@ApplicationScoped
@Path("kitchen-service")
public class KitchenServiceResource {

    @Inject
    KitchenDAOImpl kitchenDAO;

    /**
     * Receives an order message, processes it, and returns the recipe information.
     *
     * @param message The order message in JSON format.
     * @return The recipe information as a string.
     */
    @Incoming("kitchen-in")
    @Outgoing("recipes")
    @Merge
    @Broadcast
    @Blocking
    @Timed("kitchen.service.time.to.receive.order")
    public String receiveOrder(String message) {
        Gson gson = new Gson();
        Order order = gson.fromJson(message, Order.class);
        Recipe recipe;
        System.out.println("Message Received: " + message);
        recipe = kitchenDAO.retrieveReceipeFromDB(order.getName());
        System.out.println(recipe.toString());
        return recipe.toString();
    }
    
    @GET
    @Path("get-all-recipes")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieves all recipes from the KitchenRecipeDB in JSON format")
    public List<Recipe> getAllRecipeFromDB(){
        return kitchenDAO.getAllRecipesFromDB();
    }

}
