package org.sbaeker.quarkus.microservices.resource;

import io.opentracing.Span;
import io.opentracing.Tracer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.json.JSONObject;
import org.sbaeker.quarkus.microservices.dao.OrderDAOImpl;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.proxy.ProductServiceProxy;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 * <p>
 * Resource class representing the Order Service REST endpoint.
 * This class handles incoming requests related to placing orders.
 */


@ApplicationScoped
@Path("order-service")
public class OrderServiceResource {

    private static final Logger LOG = Logger.getLogger(OrderServiceResource.class);

    @Inject
    OrderDAOImpl orderDAO;

    @RestClient
    ProductServiceProxy productServiceProxy;

    /**
     * Places an order in the Order Service.
     *
     * @param order the order to be placed, provided as a JSON object
     * @return a response indicating the status of the order placement and the order details
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Takes in an order from the Order Service Endpoint with two Query Parameters: Name and Price both in String format")
    public Response placeOrder(Order order) {
        JSONObject order_json = new JSONObject(order);
        order.setName(order_json.getString("name"));
        order.setPrice(order_json.getString("price"));
        order.setID(order_json.getInt("ID"));
        orderDAO.writeOrderToDd(order);
        System.out.println(order_json);
        productServiceProxy.handleIncomingOrders(String.valueOf(order_json));
        LOG.info("OrderServiceResource.class - Method: placeOrder(String, String)");
        return Response.ok(201).entity(order.toString()).build();
    }

}
