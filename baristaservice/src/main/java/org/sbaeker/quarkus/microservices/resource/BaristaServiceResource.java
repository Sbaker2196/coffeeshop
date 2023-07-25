package org.sbaeker.quarkus.microservices.resource;

import com.google.gson.Gson;
import io.micrometer.core.annotation.Timed;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.*;
import org.sbaeker.quarkus.microservices.dao.BaristaDAOImpl;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.model.Recipe;

/**
 * Represents a resource for the Barista service.
 *
 * <p>
 * The BaristaServiceResource class is responsible for receiving orders,
 * retrieving the corresponding recipe from the database, and returning the
 * recipe as a string.
 *
 * <p>
 * Usage example:
 *
 * <pre>{@code
 * BaristaServiceResource baristaService = new BaristaServiceResource();
 * String orderMessage = "{\"name\":\"Cappuccino\", \"price\":\"3.99\"}";
 * String recipe = baristaService.receiveOrder(orderMessage);
 * System.out.println("Recipe: " + recipe);
 * }</pre>
 *
 * <p>
 * The BaristaServiceResource class is an application-scoped CDI bean and
 * uses dependency injection to obtain an instance of the BaristaDAOImpl class.
 *
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
@ApplicationScoped
public class BaristaServiceResource {

    @Inject
    BaristaDAOImpl baristaDAO;

    /**
     * Receives an order message and retrieves the corresponding recipe from the
     * database.
     *
     * @param message The order message to be processed.
     * @return The recipe as a string.
     */
    @Incoming("barista-in")
    @Outgoing("recipes")
    @Merge
    @Broadcast
    @Blocking
    @Timed("barista.service.time.to.receive.order")
    public String receiveOrder(String message) {
        Gson gson = new Gson();
        Order order = gson.fromJson(message, Order.class);
        Recipe recipe;
        System.out.println("Message Received: " + message);
        recipe = baristaDAO.retrieveRecipeFromDB(order.getName());
        System.out.println(recipe.toString());
        return recipe.toString();
    }
}
