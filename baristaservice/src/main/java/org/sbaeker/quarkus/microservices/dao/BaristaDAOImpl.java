package org.sbaeker.quarkus.microservices.dao;

import io.quarkus.agroal.DataSource;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.model.Recipe;

@ApplicationScoped
public class BaristaDAOImpl implements BaristaDAO {

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional
    public void writeOrderToDB(Order order) {
        entityManager.persist(order);
    }

    @Override
    @Transactional
    public Recipe retrieveReceipeFromDB(String name) {
        return  entityManager.createQuery("SELECT r FROM Recipe r WHERE r.name = :name", Recipe.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
