package org.sbaeker.quarkus.microservices.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;

/**
 * Represents an order in the coffee shop.
 *
 * <p>The Order class encapsulates the details of a coffee order, including the name and price of the item.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Order order = new Order();
 * order.setName("Cappuccino");
 * order.setPrice("3.50");
 * System.out.println("Order: " + order);
 * }</pre>
 *
 * <p>The Order class is designed to be serializable and can be used in distributed environments.</p>
 *
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
@ApplicationScoped
public class Order implements Serializable{

        private String name;
        private String price;

        public Order() {
        }

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
            return "name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    '}';
        }
}


