package org.sbaeker.quarkus.microservices.resource;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 *
 * The BaristaServiceResource class is responsible for receiving orders, processing them, and sending the corresponding recipes.
 * It acts as a resource endpoint for the barista service.
 *
 * This class uses reactive messaging annotations to handle the incoming and outgoing messages.
 * It interacts with the barista DAO implementation to perform database operations.
 *
 * The class is application-scoped, meaning that there is only one instance shared across the application.
 *
 * Note: The actual implementation details of the methods are not described in the Javadoc comments.
 */

import com.google.gson.Gson;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.*;
import org.sbaeker.quarkus.microservices.dao.BaristaDAOImpl;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.model.Recipe;

@ApplicationScoped
public class BaristaServiceResource {

    @Inject
    private BaristaDAOImpl baristaDAO;
    /**
     * Receives an order message, processes it, and sends the corresponding recipe.
     *
     * This method is annotated with various reactive messaging annotations to define its behavior.
     * It converts the incoming message into an Order object, performs database operations using the Barista DAO,
     * and returns the corresponding recipe as a string.
     *
     * @param message the incoming order message
     * @return the recipe as a string
     */
    @Incoming("barista-in")
    @Outgoing("recipes")
    @Merge
    @Broadcast
    @Blocking
    public String receiveOrder(String message) {
        Gson gson = new Gson();
        Order order = gson.fromJson(message, Order.class);
        Recipe recipe;
        System.out.println("Message Received: " + message);
        baristaDAO.writeOrderToDB(order);
        recipe = baristaDAO.retrieveReceipeFromDB(order.getName());
        System.out.println(recipe.toString());
        return recipe.toString();
    }

}
