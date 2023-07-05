package org.sbaeker.quarkus.microservices.classification;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import org.json.JSONObject;

/**
 * The Classifier class is responsible for classifying orders based on their names. It provides a
 * method to classify an order by checking if its name matches predefined lists of drinks and foods.
 *
 * <p>Usage example:
 *
 * <pre>{@code
 * Classifier classifier = new Classifier();
 * String classification = classifier.classifyOrder(orderMessage);
 * }</pre>
 *
 * <p>The Classifier class is annotated with {@code @ApplicationScoped} to indicate that it is
 * managed by the application container.
 *
 * <p>The {@code classifyOrder} method takes a message as input and returns a classification result
 * as a string. It checks if the name of the order matches any of the predefined lists of drinks or
 * foods. If a match is found, it returns the corresponding classification ("drink" or "food").
 * Otherwise, it returns the original message.
 *
 * <p>The predefined lists of drinks and foods are defined as arrays of strings.
 *
 * <p>Note: This classifier assumes that the message is in JSON format and contains a "name" field.
 *
 * @see ApplicationScoped
 * @since 1.0
 * @author Sean Bäker
 * @version 1.0.0
 */
@ApplicationScoped
public class Classifier {

  /**
   * Classifies an order based on its name.
   *
   * @param message the order message to be classified
   * @return the classification result as a string ("drink", "food") or the original message if no
   *     match is found
   */
  public String classifyOrder(String message) {
    String[] drinks = {"espresso", "americano", "cappuccino", "latte", "latte macchiato"};
    String[] foods = {"croissant", "bagel", "cheesecake", "sandwich"};
    JSONObject message_json = new JSONObject(message);

    if (Arrays.asList(drinks).contains(message_json.getString("name"))) {
      System.out.println("Drink");
      return "drink";
    }

    if (Arrays.asList(foods).contains(message_json.getString("name"))) {
      System.out.println("Food");
      return "food";
    }
    return message;
  }
}
