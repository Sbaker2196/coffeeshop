CREATE DATABASE baristaorders;
\c baristaorders;

CREATE TABLE orders(
    orderID SERIAl PRIMARY KEY,
    name VARCHAR(255),
    price VARCHAR(255)
);