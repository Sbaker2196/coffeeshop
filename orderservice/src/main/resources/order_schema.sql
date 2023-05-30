CREATE SCHEMA IF NOT EXISTS order_schema;

USE order_schema;

CREATE TABLE IF NOT EXISTS orders(
    orderID INT,
    name VARCHAR(255),
    price VARCHAR(255)
);