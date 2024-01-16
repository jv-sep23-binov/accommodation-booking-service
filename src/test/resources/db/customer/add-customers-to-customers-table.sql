INSERT INTO customers (id, email, first_name, last_name, password)
VALUES (4, 'test@gmail.com', 'John', 'Doe', 'password');
INSERT INTO customers_roles (customer_id, role_id)
VALUES (4, 1), (4, 2);
