package org.sbaeker.quarkus.microservices.model;

import java.io.Serializable;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Represents a recipe for a coffee beverage.
 *
 * <p>The Recipe class contains the details of a coffee recipe, including the amounts of water and coffee,
 * whether milk should be foamed, and the name of the recipe.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Recipe recipe = new Recipe();
 * recipe.setName("Cappuccino");
 * recipe.setGramsWater(100);
 * recipe.setGramsCoffee(10);
 * recipe.setMilkFoamed(true);
 * System.out.println("Recipe: " + recipe);
 * }</pre>
 *
 * <p>The Recipe class is mapped to the "recipe" table in the database using JPA annotations.</p>
 *
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
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
