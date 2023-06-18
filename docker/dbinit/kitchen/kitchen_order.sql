CREATE DATABASE kitchenorders;
\c kitchenorders;

CREATE TABLE orders(
    orderID SERIAL PRIMARY KEY,
    name VARCHAR(255),
    price VARCHAR(255)
);