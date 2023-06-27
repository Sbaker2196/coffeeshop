package org.sbaeker.quarkus.microservices.dao;


import io.micrometer.core.annotation.Timed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.sbaeker.quarkus.microservices.model.Order;

/**
 * The OrderDAOImpl class is responsible for performing database operations related to orders.
 * It provides methods to write orders to the database.
 * This DAO implementation is specifically designed for handling orders.
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * OrderDAOImpl orderDAO = new OrderDAOImpl();
 * Order order = new Order();
 * order.setName("Cappuccino");
 * order.setPrice("3.99");
 * orderDAO.writeOrderToDd(order);
 * }</pre>
 *
 * <p>The OrderDAOImpl class is an application-scoped CDI bean that handles database operations for orders.</p>
 *
 * <p>The writeOrderToDd() method is responsible for persisting an order object to the database.
 * It is annotated with {@code @Timed} to enable monitoring of the method's execution time.
 * The {@code @Transactional} annotation ensures that the operation is performed within a transaction boundary.</p>
 *
 * <p>By using the {@code @Timed} annotation, detailed metrics about the method execution are collected,
 * including the time it takes to write the order to the database.
 * This information can be used for performance analysis and monitoring.</p>
 *
 * <p>The Order object passed as a parameter is persisted to the database using the injected EntityManager.</p>
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

    @Inject
    private EntityManager entityManager;

    //Mit der Annotation hingegen bekommen wir detailliertere Informationen
    //zur Metrik dazu geliefert wie:
    //time_to_write_to_order_db_seconds_sum{class="org.sbaeker.quarkus.microservices.dao.OrderDAOImpl", exception="none",
    //                                      instance="127.0.0.1:8080", job="order-service", method="writeOrderToDd"}

    /**
     * Writes an order to the database.
     *
     * @param order The order to be written to the database.
     */
    @Override
    @Timed("order.service.time.to.write.to.order.db")
    @Transactional
    public void writeOrderToDd(Order order){
        entityManager.persist(order);
    }
}
