package org.sbaeker.quarkus.microservices.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.json.JSONObject;
import org.sbaeker.quarkus.microservices.dao.OrderDAOImpl;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.proxy.ProductServiceProxy;
import io.micrometer.core.instrument.MeterRegistry;

import java.sql.SQLException;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 * 
 * Resource class representing the Order Service REST endpoint.
 * This class handles incoming requests related to placing orders.
 */
@ApplicationScoped
@Path("order-service")
public class OrderServiceResource {

    private static final Logger LOG = Logger.getLogger(OrderServiceResource.class);

    @Inject
    private OrderDAOImpl orderDAO;

    //Mit der default Registry fließen weniger Informationen an Prometheus
    //amount_of_orders_placed_total{instance="127.0.0.1:8080", job="order-service"}
    private final MeterRegistry registry;

    OrderServiceResource(MeterRegistry registry){
        this.registry = registry;
    }

    @RestClient
    private ProductServiceProxy productServiceProxy;
    /**
     * Places an order in the Order Service.
     *
     * @param order the order to be placed, provided as a JSON object
     * @return a response indicating the status of the order placement and the order details
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Takes in an order in JSON format from the GUI placed by a customer ")
    public Response placeOrder(Order order) throws SQLException {
        JSONObject order_json = new JSONObject(order);
        order.setName(order_json.getString("name"));
        order.setPrice(order_json.getString("price"));
        orderDAO.writeOrderToDd(order);
        System.out.println(order_json);
        productServiceProxy.handleIncomingOrders(String.valueOf(order_json));
        LOG.info("OrderServiceResource.class - Method: placeOrder(String, String)");
        registry.counter("amount.of.orders.placed").increment();
        return Response.ok(201).entity(order.toString()).build();
    }

}
