CREATE DATABASE baristarecipes;
\c baristarecipes;

CREATE TABLE recipe(
    name VARCHAR(255),
    gramsWater INTEGER,
    gramsCoffee INTEGER,
    isMilkFoamed BOOLEAN
);

INSERT INTO recipe(name, gramsWater, gramsCoffee, isMilkFoamed) VALUES ('Espresso', 40, 18, FALSE);

INSERT INTO recipe(name, gramsWater, gramsCoffee, isMilkFoamed) VALUES ('Americano', 180, 18, FALSE);

INSERT INTO recipe(name, gramsWater, gramsCoffee, isMilkFoamed) VALUES ('Cappuccino', 40, 18, TRUE);

INSERT INTO recipe(name, gramsWater, gramsCoffee, isMilkFoamed) VALUES ('Latte', 40, 18, TRUE);

INSERT INTO recipe(name, gramsWater, gramsCoffee, isMilkFoamed) VALUES ('Latte Macchiato', 80, 36, TRUE);