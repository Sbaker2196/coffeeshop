package org.sbaeker.quarkus.microservices.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

/**
 * The Order class represents an order object.
 * It is an application-scoped CDI bean that models an order in the system.
 * An order object consists of a name and a price.
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Order order = new Order();
 * order.setName("Cappuccino");
 * order.setPrice("3.99");
 * System.out.println(order.toString());
 * }</pre>
 *
 * <p>The Order class is annotated with {@code @Schema} to provide additional information for OpenAPI documentation.
 * The {@code description} attribute specifies the description of the Order object.</p>
 *
 * <p>The Order class is also annotated with {@code @Entity} to indicate that it is a JPA entity.
 * The {@code @Table} annotation specifies the name of the database table associated with the Order entity.</p>
 *
 * <p>The Order class implements the {@code Serializable} interface to support serialization and deserialization of objects.</p>
 *
 * <p>The name and price properties of the Order object are annotated with {@code @Schema} to specify their requirements in the OpenAPI documentation.</p>
 *
 * <p>The toString() method is overridden to provide a string representation of the Order object in JSON format.
 * It is annotated with {@code @Produces} to indicate that the method produces a response of type {@code MediaType.APPLICATION_JSON}.</p>
 *
 * @see Schema
 * @see Entity
 * @see Table
 * @see Produces
 * @see MediaType
 * @see Serializable
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
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
