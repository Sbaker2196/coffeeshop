package org.sbaeker.quarkus.microservices.resource;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.json.JSONObject;
import org.sbaeker.quarkus.microservices.dao.OrderDAOImpl;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.proxy.ProductServiceProxy;

/**
 * The OrderServiceResource class represents a REST resource for managing
 * orders. It exposes
 * endpoints to handle incoming orders and interact with the product service.
 *
 * <p>
 * Usage example:
 *
 * <pre>{@code
 * OrderServiceResource orderServiceResource = new
 * OrderServiceResource(registry); Response response =
 * orderServiceResource.placeOrder(order);
 * }</pre>
 *
 * <p>
 * The OrderServiceResource class is annotated with {@code @ApplicationScoped}
 * to indicate that
 * it is a CDI bean.
 *
 * <p>
 * The resource endpoints are defined using JAX-RS annotations such as
 * {@code @Path},
 * {@code @POST}, {@code @Consumes}, and {@code @Produces}. The {@code @Path}
 * annotation specifies
 * the base path for the resource, while {@code @POST} indicates that the method
 * handles HTTP POST
 * requests. {@code @Consumes} and {@code @Produces} specify the media types of
 * the request and
 * response payloads.
 *
 * <p>
 * The class is also annotated with {@code @Operation} to provide a summary of
 * the placeOrder()
 * method in the OpenAPI documentation.
 *
 * <p>
 * The OrderServiceResource class has dependencies on the OrderDAOImpl and
 * ProductServiceProxy
 * beans, which are injected using {@code @Inject} and {@code @RestClient}
 * annotations,
 * respectively.
 *
 * <p>
 * The class uses the Micrometer MeterRegistry to track the number of orders
 * placed. The
 * MeterRegistry is injected through the constructor.
 *
 * <p>
 * The placeOrder() method is responsible for processing an incoming order. It
 * extracts the order
 * details from the JSON payload, writes the order to the database using the
 * OrderDAOImpl, invokes
 * the ProductServiceProxy to handle the order, increments a counter in the
 * MeterRegistry, and
 * returns a response with the order details.
 *
 * @see ApplicationScoped
 * @see Path
 * @see POST
 * @see Consumes
 * @see Produces
 * @see Operation
 * @see RestClient
 * @see Logger
 * @see JSONObject
 * @see OrderDAOImpl
 * @see Order
 * @see ProductServiceProxy
 * @see MeterRegistry
 * @see Response
 * @see MediaType
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
@ApplicationScoped
@Path("order-service")
public class OrderServiceResource {

  private static final Logger LOG =
      Logger.getLogger(OrderServiceResource.class);

  @Inject private OrderDAOImpl orderDAO;

  // Mit der default Registry fließen weniger Informationen an Prometheus
  // amount_of_orders_placed_total{instance="127.0.0.1:8080",
  // job="order-service"}
  private final MeterRegistry registry;

  /**
   * Constructs an OrderServiceResource with the specified MeterRegistry.
   *
   * @param registry The MeterRegistry used to track metrics.
   */
  OrderServiceResource(MeterRegistry registry) { this.registry = registry; }

  @RestClient private ProductServiceProxy productServiceProxy;

  /**
   * Handles incoming orders and processes them.
   *
   * @param order The order to be processed.
   * @return The response containing the order details.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
      summary =
          "Takes in an order in JSON format from the GUI placed by a customer ")
  @Timed("order.service.time.to.place.order")
  public Response
  placeOrder(Order order) {
    JSONObject order_json = new JSONObject(order);
    order.setName(order_json.getString("name"));
    order.setPrice(order_json.getString("price"));
    orderDAO.writeOrderToDd(order);
    System.out.println(order_json);
    productServiceProxy.handleIncomingOrders(String.valueOf(order_json));
    LOG.info("OrderServiceResource.class - Method: placeOrder(String, String)");
    registry.counter("order.service.amount.of.orders.placed").increment();
    return Response.ok(201).entity(order.toString()).build();
  }

  @GET
  @Path("get-all-orders")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Retrieves all orders from the DB in JSON Format")
  public List<Order> getAllOrdersFromDB() {
    return orderDAO.getAllOrdersFromDB();
  }

  @GET
  @Path("get-order-by-id/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
      summary =
          "Retrieves the order based on the given ID specified in the Path")
  public String getOrderById(@PathParam("id") int id) {
    return orderDAO.getOrderById(id);
  }

  @POST
  @Path("delete-order/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
      summary = "Deletes an order based on the given ID specified in the Path")
  public void deleteOrderById(@PathParam("id") int id) {
    orderDAO.deleteOrderById(id);
  }
}
