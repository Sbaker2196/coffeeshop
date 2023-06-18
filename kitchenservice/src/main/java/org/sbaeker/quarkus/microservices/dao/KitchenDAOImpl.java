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
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.sbaeker.quarkus.microservices.model.Recipe;

@ApplicationScoped
public class KitchenDAOImpl implements KitchenDAO {

    private static final Logger LOG = Logger.getLogger(KitchenDAOImpl.class);

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional
    public Recipe retrieveReceipeFromDB(String name) {
        LOG.info("Retrieving recipe from BaristaRecipeDB: " + name);
        String capName = StringUtils.capitalize(name);
        try{
            return  entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = :name", Recipe.class)
                    .setParameter("name", capName)
                    .getSingleResult();
        } catch (NoResultException e){
            LOG.warn("Recipe not found in BaristaRecipeDB: " + name);
            return null;
        }
    }
}
