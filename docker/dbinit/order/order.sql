CREATE DATABASE defaultorders;
\c defaultorders;

CREATE TABLE orders(
    orderID SERIAL PRIMARY KEY,
    name VARCHAR(255),
    price VARCHAR(255)
);