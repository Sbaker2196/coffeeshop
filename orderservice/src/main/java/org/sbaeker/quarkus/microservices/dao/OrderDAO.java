package org.sbaeker.quarkus.microservices.dao;

import org.sbaeker.quarkus.microservices.model.Order;

import java.sql.SQLException;

public interface OrderDAO {

    void writeOrderToDd(Order order);

}
