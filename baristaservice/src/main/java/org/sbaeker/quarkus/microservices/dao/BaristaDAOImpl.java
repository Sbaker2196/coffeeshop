package org.sbaeker.quarkus.microservices.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.sbaeker.quarkus.microservices.model.Recipe;

@ApplicationScoped
public class BaristaDAOImpl implements BaristaDAO {

    private static final Logger LOG = Logger.getLogger(BaristaDAOImpl.class);

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional
    public Recipe retrieveRecipeFromDB(String name) {
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
