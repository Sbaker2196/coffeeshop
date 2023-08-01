package org.sbaeker.quarkus.microservices.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Timed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.jboss.logging.Logger;
import org.sbaeker.quarkus.microservices.model.Order;

/**
 * The OrderDAOImpl class is responsible for performing database operations
 * related to orders. It
 * provides methods to write orders to the database. This DAO implementation is
 * specifically
 * designed for handling orders.
 *
 * <p>
 * Usage example:
 *
 * <pre>{@code
 * OrderDAOImpl orderDAO = new OrderDAOImpl();
 * Order order = new Order();
 * order.setName("Cappuccino");
 * order.setPrice("3.99");
 * orderDAO.writeOrderToDd(order);
 * }</pre>
 *
 * <p>
 * The OrderDAOImpl class is an application-scoped CDI bean that handles
 * database operations for
 * orders.
 *
 * <p>
 * The writeOrderToDd() method is responsible for persisting an order object to
 * the database. It
 * is annotated with {@code @Timed} to enable monitoring of the method's
 * execution time. The
 * {@code @Transactional} annotation ensures that the operation is performed
 * within a transaction
 * boundary.
 *
 * <p>
 * By using the {@code @Timed} annotation, detailed metrics about the method
 * execution are
 * collected, including the time it takes to write the order to the database.
 * This information can
 * be used for performance analysis and monitoring.
 *
 * <p>
 * The Order object passed as a parameter is persisted to the database using the
 * injected
 * EntityManager.
 *
 * @see Order
 * @see EntityManager
 * @see Transactional
 * @see Timed
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
@ApplicationScoped
public class OrderDAOImpl implements OrderDAO {

  @Inject EntityManager entityManager;

  private static final Logger LOG = Logger.getLogger(OrderDAOImpl.class);

  // Mit der Annotation hingegen bekommen wir detailliertere Informationen
  // zur Metrik dazu geliefert wie:
  // time_to_write_to_order_db_seconds_sum{class="org.sbaeker.quarkus.microservices.dao.OrderDAOImpl",
  // exception="none",
  // instance="127.0.0.1:8080", job="order-service",
  // method="writeOrderToDd"}

  /**
   * Writes an order to the database.
   *
   * @param order The order to be written to the database.
   */
  @Override
  @Timed("order.service.time.to.write.to.order.db")
  @Transactional
  public void writeOrderToDd(Order order) {
    entityManager.persist(order);
  }

  /**
   * Retrieves all orders from the database utilizing Hibernates entityManager
   *
   * @return the placed order as a List of results of the {@link EntityManager}
   */
  @Override
  @Timed("order.service.time.to.retrieve.orders.from.db")
  @Transactional
  public List<Order> getAllOrdersFromDB() {

    List<Order> orders = null;

    try {
      String jpql = "SELECT r FROM Order r";
      TypedQuery<Order> typedQuery =
          entityManager.createQuery(jpql, Order.class);
      orders = typedQuery.getResultList();
      LOG.info("Orders have been successfully retrived from the OrderDB");
    } catch (Exception e) {
      LOG.error("Orders could not be retrived from the DB " + e);
    }
    return orders;
  }

  /**
   * Retrieves a group of orders based on the given name specified in the Path.
   * Giving "espresso" as the parameter would lead to the retrieval of all
   * "espresso" orders.
   *
   * @param name The name of the order as a String.
   * @return The list of orders belonging to the given group.
   */
  @Override
  @Timed("order.service.time.to.get.order.by.name")
  @Transactional
  public String getOrdergroupByName(String name) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      Order order = entityManager.find(Order.class, name);
      if (order == null) {
        LOG.warn("Order not found with Name {" + name + "}");
        return "{\"error\": \"Order not found\"}";
      }
      return objectMapper.writeValueAsString(order);
    } catch (Exception e) {
      LOG.error(
          "An error occurred while processing the request. \n Exception: " + e);
      return "{\"error\": \"An error occurred while processing the request\"}";
    }
  }
}
