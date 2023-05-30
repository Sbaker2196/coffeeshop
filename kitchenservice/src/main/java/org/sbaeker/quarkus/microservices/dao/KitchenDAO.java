package org.sbaeker.quarkus.microservices.dao;


import jakarta.enterprise.context.ApplicationScoped;
import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.model.Recipe;

import java.sql.SQLException;

public interface KitchenDAO {

    void writeOrderToDB(Order order);

    Recipe retrieveReceipeFromDB(String name);

}
