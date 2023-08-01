package org.sbaeker.quarkus.microservices.dao;

import java.util.List;
import org.sbaeker.quarkus.microservices.model.Order;

public interface OrderDAO {

  void writeOrderToDd(Order order);

  List<Order> getAllOrdersFromDB();

  String getOrdergroupByName(String name);
}
