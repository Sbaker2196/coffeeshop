package org.sbaeker.quarkus.microservices.dao;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 *
 * The KitchenDAOImpl class is responsible for performing database operations related to the kitchen service.
 * It provides methods to write orders to the database and retrieve recipes from the database.
 * This DAO implementation is specifically designed for the kitchen service.
 */

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.model.Recipe;

@ApplicationScoped
public class KitchenDAOImpl implements KitchenDAO {

    @Inject
    EntityManager entityManager;

    /**
     * Writes an order to the database.
     *
     * @param order the order to be written to the database
     */
    @Override
    @Transactional
    public void writeOrderToDB(Order order) {
        entityManager.persist(order);
    }

    /**
     * Retrieves a recipe from the database based on the given name.
     *
     * @param name the name of the recipe to retrieve
     * @return the retrieved recipe
     */
    @Override
    @Transactional
    public Recipe retrieveReceipeFromDB(String name) {
        return  entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = :name", Recipe.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
