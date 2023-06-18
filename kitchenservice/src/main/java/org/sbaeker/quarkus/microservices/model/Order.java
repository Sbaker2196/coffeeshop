package org.sbaeker.quarkus.microservices.model;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;

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
