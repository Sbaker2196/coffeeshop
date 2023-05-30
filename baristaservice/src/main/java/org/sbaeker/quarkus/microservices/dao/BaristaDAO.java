package org.sbaeker.quarkus.microservices.dao;


import org.sbaeker.quarkus.microservices.model.Order;
import org.sbaeker.quarkus.microservices.model.Recipe;

import java.sql.SQLException;

public interface BaristaDAO {

    void writeOrderToDB(Order order);

    Recipe retrieveReceipeFromDB(String name);

}
