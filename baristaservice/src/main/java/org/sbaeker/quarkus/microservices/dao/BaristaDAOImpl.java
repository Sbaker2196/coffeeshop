package org.sbaeker.quarkus.microservices.dao;

import java.util.List;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.HibernateException;
import org.jboss.logging.Logger;
import org.sbaeker.quarkus.microservices.model.Recipe;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jpa.HibernateMetrics;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 * An implementation of the BaristaDAO interface that interacts with the
 * BaristaRecipeDB to retrieve
 * recipe information.
 *
 * <p>
 * The BaristaDAOImpl class provides methods to retrieve recipe details from the
 * database,
 * including searching for a recipe by name.
 *
 * <p>
 * Usage example:
 *
 * <pre>{@code
 * BaristaDAO baristaDAO = new BaristaDAOImpl();
 * Recipe recipe = baristaDAO.retrieveRecipeFromDB("Cappuccino");
 * if (recipe != null) {
 *   System.out.println("Recipe found: " + recipe.getName());
 * } else {
 *   System.out.println("Recipe not found.");
 * }
 * }</pre>
 *
 * <p>
 * Note: This class is not thread-safe.
 *
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
@ApplicationScoped
public class BaristaDAOImpl implements BaristaDAO {

  private static final Logger LOG = Logger.getLogger(BaristaDAOImpl.class);

  @Inject
  EntityManager entityManager;

  private final MeterRegistry registry;

  BaristaDAOImpl(MeterRegistry registry) {
    this.registry = registry;
  }

  /**
   * Retrieves a recipe from the BaristaRecipeDB based on the specified name.
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
  @Timed("barista.service.time.to.retrieve.recipe.from.db")
  public Recipe retrieveRecipeFromDB(String name) {
    try {
      LOG.info("Retrieving recipe from BaristaRecipeDB: " + name);
      String capName = WordUtils.capitalizeFully(name);
      Recipe recipe = entityManager
          .createQuery("SELECT r FROM Recipe r WHERE r.name = :name", Recipe.class)
          .setParameter("name", capName)
          .getSingleResult();
      LOG.info("Recipe successfully retrieved from BaristaRecipeDB: " + recipe.toString());
      return recipe;
    } catch (HibernateException e) {
      registry.counter("barista.service.amount.of.failed.recipe.retrievals.from.db");
      LOG.error("Error retrieving recipe from the DB: " + e.getMessage());
      return null;
    }

  }

  @Override
  @Transactional
  @Timed("barista.service.time.to.retrive.all.recipes.from.db")
  public List<Recipe> getAllRecipesFromDB() {
    List<Recipe> recipes = null;
    try {
      String jpql = "SELECT r FROM Recipe r";
      TypedQuery<Recipe> typedQuery = entityManager.createQuery(jpql, Recipe.class);
      recipes = typedQuery.getResultList();
      LOG.info("Orders have been successfully retrieven from the BaristaRecipeDB");
      return recipes;
    } catch (HibernateException e) {
      registry.counter("barista.service.amount.of.failed.recipe.retrievals.all.from.db");
      LOG.error("Error retrieving all recipes from DB: " + e.getMessage());
      return null;
    }
  }
}
