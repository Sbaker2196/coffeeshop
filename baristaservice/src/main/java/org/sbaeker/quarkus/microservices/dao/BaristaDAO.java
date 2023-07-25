package org.sbaeker.quarkus.microservices.dao;

import jakarta.transaction.Transactional;
import org.sbaeker.quarkus.microservices.model.Recipe;

public interface BaristaDAO {
    @Transactional 
    Recipe retrieveRecipeFromDB(String name);
}
