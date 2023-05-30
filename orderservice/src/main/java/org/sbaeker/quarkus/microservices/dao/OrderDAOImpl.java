package org.sbaeker.quarkus.microservices.dao;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 */

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.sbaeker.quarkus.microservices.model.Order;

/**
 * Implementation class for the OrderDAO interface.
 * Provides methods to interact with the database for order-related operations.
 */

@ApplicationScoped
public class OrderDAOImpl implements OrderDAO {

    @Inject
    EntityManager entityManager;

    /**
     * Writes an order to the database.
     *
     * @param order the order to be written
     */
    @Override
    @Transactional
    public void writeOrderToDd(Order order) {
        entityManager.persist(order);
    }

    /**
     * Retrieves a list of orders from the database.
     *
     * @return a JSON string representing the list of orders
     */
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public String getOrders() {
        Query query = entityManager.createQuery("SELECT * from order");
        return query.getResultList().toString();
    }
}
