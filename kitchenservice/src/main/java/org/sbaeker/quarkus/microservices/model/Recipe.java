package org.sbaeker.quarkus.microservices.model;

import java.io.Serializable;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The Recipe class represents a recipe in the system.
 * It encapsulates the ingredients and quantities required to prepare a specific recipe.
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * Recipe recipe = new Recipe();
 * recipe.setName("Cappuccino");
 * recipe.setGramsWater(200);
 * recipe.setGramsSugar(10);
 * recipe.setGramsButter(15);
 * recipe.setNumOfSalamiSlices(4);
 * recipe.setGramsCreamCheese(50);
 * System.out.println(recipe.toString());
 * }</pre>
 *
 * <p>The Recipe class is an entity mapped to the "recipe" table in the database.</p>
 *
 * <p>The toString() method of the Recipe class provides a string representation of the recipe,
 * including the name, and the quantities of various ingredients.</p>
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
@Entity
@Table(name = "recipe")
public class Recipe implements Serializable{

    @Id
    @Schema(required = true)
    private String name;
    private double gramsWater;
    private double gramsSugar;
    private double gramsButter;
    private double numOfSalamiSlices;
    private double gramsCreamCheese;

    public Recipe(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGramsWater() {
        return gramsWater;
    }

    public void setGramsWater(double gramsWater) {
        this.gramsWater = gramsWater;
    }

    public double getGramsSugar() {
        return gramsSugar;
    }

    public void setGramsSugar(double gramsSugar) {
        this.gramsSugar = gramsSugar;
    }

    public double getGramsButter() {
        return gramsButter;
    }

    public void setGramsButter(double gramsButter) {
        this.gramsButter = gramsButter;
    }

    public double getNumOfSalamiSlices() {
        return numOfSalamiSlices;
    }

    public void setNumOfSalamiSlices(double numOfSalamiSlices) {
        this.numOfSalamiSlices = numOfSalamiSlices;
    }

    public double getGramsCreamCheese() {
        return gramsCreamCheese;
    }

    public void setGramsCreamCheese(double gramsCreamCheese) {
        this.gramsCreamCheese = gramsCreamCheese;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name=" + name +
                ", gramsWater=" + gramsWater +
                ", gramsSugar=" + gramsSugar +
                ", gramsButter=" + gramsButter +
                ", numOfSalamiSlices=" + numOfSalamiSlices +
                ", gramsCreamCheese=" + gramsCreamCheese +
                '}';
    }
}
