package org.sbaeker.quarkus.microservices.dao;

import java.util.List;

import org.sbaeker.quarkus.microservices.model.Recipe;

public interface KitchenDAO {

  Recipe retrieveReceipeFromDB(String name);

  List<Recipe> getAllRecipesFromDB();
  
}
