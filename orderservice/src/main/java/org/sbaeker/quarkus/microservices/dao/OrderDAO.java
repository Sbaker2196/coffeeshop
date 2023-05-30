package org.sbaeker.quarkus.microservices.dao;

import org.sbaeker.quarkus.microservices.model.Order;

public interface OrderDAO {

    void writeOrderToDd(Order order);
    String getOrders();

}
