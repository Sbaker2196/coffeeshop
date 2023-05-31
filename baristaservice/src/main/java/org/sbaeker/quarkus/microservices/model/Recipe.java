package org.sbaeker.quarkus.microservices.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import java.io.Serializable;

@ApplicationScoped
@Entity
@Table(name = "recipe")
public class Recipe implements Serializable {

    @Column(nullable = true)
    private double gramsWater;
    @Column(nullable = true)
    private double gramsCoffee;
    @Column(nullable = true)
    private boolean isMilkFoamed;
    @Id
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
