package org.sbaeker.quarkus.microservices.resource;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 *
 * The KitchenServiceResource class is responsible for receiving orders, processing them, and sending the corresponding recipes.
 * It acts as a resource endpoint for the kitchen service.
 *
 * This class uses reactive messaging annotations to handle the incoming and outgoing messages.
 * It interacts with the kitchen DAO implementation to perform database operations.
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
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.sbaeker.quarkus.microservices.dao.KitchenDAOImpl;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.model.Recipe;

@ApplicationScoped
public class KitchenServiceResource {

    @Inject
    private KitchenDAOImpl kitchenDAO;

    /**
     * Receives an order message, processes it, and sends the corresponding recipe.
     *
     * This method is annotated with various reactive messaging annotations to define its behavior.
     * It converts the incoming message into an Order object, performs database operations using the kitchen DAO,
     * and returns the corresponding recipe as a string.
     *
     * @param message the incoming order message
     * @return the recipe as a string
     */
    @Incoming("kitchen-in")
    @Outgoing("recipes")
    @Merge
    @Broadcast
    @Blocking
    public String receiveOrder(String message) {
        Gson gson = new Gson();
        Order order = gson.fromJson(message, Order.class);
        Recipe recipe;
        System.out.println("Message Received: " + message);
        kitchenDAO.writeOrderToDB(order);
        recipe = kitchenDAO.retrieveReceipeFromDB(order.getName());
        return recipe.toString();
    }


}
