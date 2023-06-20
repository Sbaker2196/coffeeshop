package org.sbaeker.quarkus.microservices.dao;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 */

import io.micrometer.core.annotation.Timed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.sbaeker.quarkus.microservices.model.Order;

/**
 * Implementation class for the OrderDAO interface.
 * Provides methods to interact with the database for order-related operations.
 */

@ApplicationScoped
public class OrderDAOImpl implements OrderDAO {

    @Inject
    private EntityManager entityManager;

    /**
     * Writes an order to the database.
     *
     * @param order the order to be written
     */

    //Mit der Annotation hingegen bekommen wir detailliertere Informationen
    //zur Metrik dazu geliefert wie:
    //time_to_write_to_order_db_seconds_sum{class="org.sbaeker.quarkus.microservices.dao.OrderDAOImpl", exception="none",
    //                                      instance="127.0.0.1:8080", job="order-service", method="writeOrderToDd"}
    @Override
    @Timed("time-to-write-to-order-db")
    @Transactional
    public void writeOrderToDd(Order order){
        entityManager.persist(order);
    }
}
