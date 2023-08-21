package org.sbaeker.quarkus.microservices.dao;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.jboss.logging.Logger;
import org.sbaeker.quarkus.microservices.model.Recipe;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 * The KitchenDAOImpl class is responsible for performing database operations
 * related to the kitchen
 * service. It provides methods to write orders to the database and retrieve
 * recipes from the
 * database. This DAO implementation is specifically designed for the kitchen
 * service.
 *
 * <p>
 * Usage example:
 *
 * <pre>{@code
 * KitchenDAOImpl kitchenDAO = new KitchenDAOImpl();
 * Recipe recipe = kitchenDAO.retrieveRecipeFromDB("Cappuccino");
 * if (recipe != null) {
 *     System.out.println("Recipe found: " + recipe.toString());
 * } else {
 *     System.out.println("Recipe not found");
 * }
 * }</pre>
 *
 * <p>
 * The KitchenDAOImpl class is an application-scoped CDI bean and uses
 * dependency injection to
 * obtain an instance of the EntityManager class.
 *
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 */
@ApplicationScoped
public class KitchenDAOImpl implements KitchenDAO {

  private static final Logger LOG = Logger.getLogger(KitchenDAOImpl.class);

  @Inject EntityManager entityManager;

  private final MeterRegistry registry;

  KitchenDAOImpl(MeterRegistry registry) { this.registry = registry; }

  /**
   * Retrieves a recipe from the KitchenRecipeDB based on the specified name.
   *
   * <p>
   * This method searches for a recipe in the database with a matching name. If
   * found, it returns
   * the Recipe object representing the recipe. If no recipe is found, it
   * returns null.
   *
   * @param name the name of the recipe to retrieve
   * @return the Recipe object representing the retrieved recipe, or null if the
   *         recipe is not found
   * @throws IllegalArgumentException               if the name parameter is
   *                                                null
   *                                                or empty
   * @throws javax.persistence.PersistenceException if an error occurs during
   *                                                the
   *                                                database operation
   * @see Recipe
   */
  @Override
  @Transactional
  @Timed("kitchen.service.time.to.retrieve.recipe.from.db")
  public Recipe retrieveReceipeFromDB(String name) {
    LOG.info("Retrieving recipe from KitchenRecipeDB: " + name);
    String capName = WordUtils.capitalize(name);
    try {
      Recipe recipe =
          entityManager
              .createQuery("SELECT r FROM Recipe r WHERE r.name = :name",
                           Recipe.class)
              .setParameter("name", capName)
              .getSingleResult();
      LOG.info("Recipe successfully retrieved from KitchenRecipeDB: " +
               recipe.toString());
      return recipe;
    } catch (NoResultException e) {
      registry.counter("kitchen.service.amount.of.failed.db.retrievals");
      LOG.warn("Recipe not found in BaristaRecipeDB: " + name);
      return null;
    }
  }

  public List<Recipe> getAllRecipesFromDB() {
    List<Recipe> recipes = null;
    try {
      String jpql = "SELECT r FROM Recipe r";
      TypedQuery<Recipe> typedQuery =
          entityManager.createQuery(jpql, Recipe.class);
      recipes = typedQuery.getResultList();
      LOG.info(
          "Recipes have been successfully retrieved from the KitchenRecipeDB");
    } catch (Exception e) {
      LOG.error("Orders could not be retrieved from the DB " + e);
    }
    return recipes;
  }
}
