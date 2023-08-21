package org.sbaeker.quarkus.microservices.model;


import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * The Order class represents an order in the system.
 * It encapsulates the name and price of the order.
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Order order = new Order();
 * order.setName("Cappuccino");
 * order.setPrice("$3.50");
 * System.out.println(order.toString());
 * }</pre>
 *
 * <p>The Order class is an application-scoped CDI bean and is serializable.</p>
 *
 * <p>The toString() method of the Order class is overridden to provide a JSON representation
 * of the order when using the MediaType.APPLICATION_JSON media type.</p>
 *
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
@ApplicationScoped
public class Order implements Serializable {

    private String name;
    private String price;

    public Order() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
