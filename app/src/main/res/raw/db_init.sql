CREATE TABLE category (
    category_id INT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL,
    PRIMARY KEY(category_id)
)

CREATE TABLE product {
    product_id INT NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    product_desc VARCHAR(255) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    PRIMARY KEY(product_id)
}

CREATE TABLE customer {
    customer_id INT NOT NULL AUTO_INCREMENT,
    customer_first_name VARCHAR(255) NOT NULL,
    customer_last_name VARCHAR(255) NOT NULL,
    PRIMARY KEY(customer_id)
}

CREATE TABLE customer_orders {
    order_id INT NOT NULL AUTO_INCREMENT,
    customer_id REFERENCES customer(customer_id),
    date_created DATE NOT NULL,
    order_status ENUM('open', 'completed', 'dispatched') NOT NULL,
    payment_method VARCHAR(255),
    PRIMARY KEY(cart_id)
}

CREATE TABLE order_items {
    order_item_id INT NOT NULL AUTO_INCREMENT,
    FOREIGN KEY (order_id) REFERENCES customer_order(order_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    quantity INT NOT NULL,
    unit_price DOUBLE NOT NULL,
    total_price DOUBLE NOT NULL,
    sales_tax DOUBLE NOT NULL,
    discount DOUBLE NOT NULL,
    PRIMARY KEY(order_item_id)
}
