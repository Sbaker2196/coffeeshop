package org.sbaeker.quarkus.microservices.model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import java.io.Serializable;

@ApplicationScoped
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
