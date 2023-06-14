package org.sbaeker.quarkus.microservices.model;

import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.io.Serializable;

@ApplicationScoped
@PersistenceUnit("recipe")
@Schema(description = "The Recipe object")
@Entity
@Table(name = "recipe")
public class Recipe implements Serializable {

    @Id
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
