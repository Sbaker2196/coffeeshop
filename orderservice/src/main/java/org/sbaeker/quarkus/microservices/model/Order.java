package org.sbaeker.quarkus.microservices.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

@ApplicationScoped
@Schema(description = "The Order object")
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @Schema(required = true)
    private String name;
    @Schema(required = true)
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