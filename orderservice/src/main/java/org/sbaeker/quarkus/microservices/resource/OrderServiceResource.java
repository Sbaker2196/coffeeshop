package org.sbaeker.quarkus.microservices.resource;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.json.JSONObject;
import org.sbaeker.quarkus.microservices.dao.OrderDAOImpl;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.proxy.ProductServiceProxy;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * The OrderServiceResource class represents a REST resource for managing
 * orders. It exposes
 * endpoints to handle incoming orders and interact with the product service.
 *
 * <p>
 * Usage example:
 *
 * <pre>{@code
 * OrderServiceResource orderServiceResource = new OrderServiceResource(registry);
 * Response response = orderServiceResource.placeOrder(order);
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

  @Inject
  private OrderDAOImpl orderDAO;

  // Mit der default Registry fließen weniger Informationen an Prometheus
  // amount_of_orders_placed_total{instance="127.0.0.1:8080",
  // job="order-service"}
  private final MeterRegistry registry;

  /**
   * Constructs an OrderServiceResource with the specified MeterRegistry.
   *
   * @param registry The MeterRegistry used to track metrics.
   */
  OrderServiceResource(MeterRegistry registry) {
    this.registry = registry;
  }

  @RestClient
  private ProductServiceProxy productServiceProxy;

  /**
   * Handles incoming orders and processes them. This method utilizes the
   * {@link OrderDAOImpl} class
   * to handle database operations.
   *
   * @param order The order to be processed.
   * @return The response containing the order details.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Takes in an order in JSON format from the GUI placed by a customer ")
  @Timed("order.service.time.to.place.order")
  @Counted("order.service.amount.of.orders.place.total")
  public Response placeOrder(Order order) {
    JSONObject order_json = new JSONObject(order);
    order.setName(order_json.getString("name"));
    order.setPrice(order_json.getString("price"));
    orderDAO.writeOrderToDd(order);
    System.out.println(order_json);
    productServiceProxy.handleIncomingOrders(String.valueOf(order_json));
    return Response.ok(200).entity(order.toString()).build();
  }

  /**
   * Retrieves all placed orders from the Databse and returns them as a JSON
   * Array. This method utilizies the {@link OrderDAOImpl} class to handle
   * database operations.
   *
   * @return JSON Array of orders
   */
  @GET
  @Path("get-all-orders")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Retrieves all orders from the DB in JSON Format")
  public Response getAllOrdersFromDB() {
    List<Order> orders = orderDAO.getAllOrdersFromDB();
    return Response.ok(orders).build();
  }

  /**
   * Gets a group of orders based on the name of the order i.e. "epsresso"
   * returns all orders that have the name "espresso".
   * This method utilizies the {@link OrderDAOImpl} class to handle database
   * operations.
   *
   * @param name The name of the order as a String
   * @return the group of orders as a JSON Array
   */
  @GET
  @Path("get-ordergroup-by-name")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(summary = "Retrieves a group of orders based on the given name in the specified Path")
  public Response getOrdergroupByName(@QueryParam("groupName") String name) {
    List<Order> orders = orderDAO.getOrdergroupByName(name);
    return Response.ok(orders).build();
  }

}
