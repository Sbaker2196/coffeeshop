package org.sbaeker.quarkus.microservices.classification;

/**
 * @author Sean Bäker
 * @version 1.0
 * @since 26.05.2023
 *
 * The Classifier class is responsible for classifying orders based on their names.
 * It categorizes orders as either drinks or foods.
 */

import jakarta.enterprise.context.ApplicationScoped;
import org.json.JSONObject;

import java.util.Arrays;

@ApplicationScoped
public class Classifier {

    /**
     * Classifies the order based on its name.
     *
     * @param message the order message to be classified
     * @return a string representing the classification ("drink" or "food") or the original message if no classification is found
     */
    public String classifyOrder(String message){
        String[] drinks = {"espresso", "americano", "cappuccino", "latte", "latte macchiato"};
        String[] foods = {"croissant", "bagel", "cheese cake", "sandwich"};
        JSONObject message_json = new JSONObject(message);

        if(Arrays.asList(drinks).contains(message_json.getString("name"))){
            System.out.println("Drink");
            return "drink";
        }

        if(Arrays.asList(foods).contains(message_json.getString("name"))){
            System.out.println("Food");
            return "food";
        }
        return message;
    }

}
