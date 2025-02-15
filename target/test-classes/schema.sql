CREATE TABLE CUSTOMERS (
    customer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    customer_date DATE,
    isvip BOOLEAN,
    status_code VARCHAR(20),
    created_on TIMESTAMP,
    modified_on TIMESTAMP
);