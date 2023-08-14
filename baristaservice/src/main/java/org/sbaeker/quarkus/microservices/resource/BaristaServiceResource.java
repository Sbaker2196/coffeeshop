package org.sbaeker.quarkus.microservices.resource;

import com.google.gson.Gson;
import io.micrometer.core.annotation.Timed;
import io.quarkus.hibernate.orm.runtime.Hibernate;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import io.smallrye.reactive.messaging.annotations.Merge;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.reactive.messaging.*;
import org.hibernate.HibernateException;
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
@Path("barista-service")
public class BaristaServiceResource {

    @Inject
    BaristaDAOImpl baristaDAO;

    private static final Logger LOG = Logger.getLogger(BaristaServiceResource.class);

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
    public Response receiveOrder(String message) {

        Gson gson = new Gson();
        Order order = gson.fromJson(message, Order.class);
        Recipe recipe;
        try {
            recipe = baristaDAO.retrieveRecipeFromDB(order.getName());
            LOG.info("Recipe retrieved successfully");
            return Response.ok(recipe).build();
        } catch (HibernateException e) {
            LOG.error("Error retrieving recipe from DB: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving recipe").build();
        }

    }

    @GET
    @Path("get-all-recipes")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed("barista.service.time.to.get.all.recipes")
    @Operation(summary = "Retrieves all order from the BaristaRecipeDB in JSON Format")
    public Response getAllRecipesFromDB() {

        try {
            List<Recipe> orders = baristaDAO.getAllRecipesFromDB();
            LOG.info("Recipes retrieved successfully");
            return Response.ok(orders).build();
        } catch (HibernateException e) {
            LOG.error("Error retrieving recipes from DB: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving recipes").build();
        }
    }

}
