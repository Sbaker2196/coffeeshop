package org.sbaeker.quarkus.microservices.model;

import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

@Entity
@Table(name = "recipe")
public class Recipe implements Serializable{

    @Schema(required = true)
    private double gramsWater;
    @Schema(required = true)
    private double gramsCoffee;
    @Schema(required = true)
    private boolean isMilkFoamed;
    @Id
    @Schema(required = true)
    private String name;

    public Recipe(){}

    public double getGramsWater() {
        return gramsWater;
    }

    public void setGramsWater(double gramsWater) {
        this.gramsWater = gramsWater;
    }

    public double getGramsCoffee() {
        return gramsCoffee;
    }

    public void setGramsCoffee(double gramsCoffee) {
        this.gramsCoffee = gramsCoffee;
    }

    public boolean isMilkFoamed() {
        return isMilkFoamed;
    }

    public void setMilkFoamed(boolean milkFoamed) {
        isMilkFoamed = milkFoamed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name=" + name +
                ", gramsWater=" + gramsWater +
                ", gramsCoffee=" + gramsCoffee +
                ", isMilkFoamed=" + isMilkFoamed +
                '}';
    }
}
