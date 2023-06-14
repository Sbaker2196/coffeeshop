CREATE TABLE recipe{
    name VARCHAR(255),
    gramsWater INTEGER,
    gramsCoffee INTEGER,
    isMilkFoamed BOOLEAN
}

INSERT INTO recipe(name, gramsWater, gramsCoffee, isMilkFoamed) VALUES("Espresso", 40, 18.0, FALSE);

INSERT INTO recipe(name, gramsWater, gramsCoffee, isMilkFoamed) VALUES("Americano", 180, 18.0, FALSE);

INSERT INTO recipe(name, gramsWater, gramsCoffee, isMilkFoamed) VALUES("Cappuccino", 40, 18.0, TRUE);

INSERT INTO recipe(name, gramsWater, gramsCoffee, isMilkFoamed) VALUES("Latte", 40, 18.0, TRUE);

INSERT INTO recipe(name, gramsWater, gramsCoffee, isMilkFoamed) VALUES("Latte Macchiato", 80, 36.0, TRUE);