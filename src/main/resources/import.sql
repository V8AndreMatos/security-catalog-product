INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('André', 'Brown', 'andre@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Soraia', 'Green', 'soraia@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_category (name) VALUES ('Livros');
INSERT INTO tb_category (name) VALUES ('Eletrônicos');
INSERT INTO tb_category (name) VALUES ('Computadores');
INSERT INTO tb_product (name, price, description) VALUES ('The Lord of the Rings', 90.5, 'Lorem ipsum dolor sit amet.');
INSERT INTO tb_product (name, price, description) VALUES ('Smart TV', 2190.0, 'Lorem ipsum dolor sit amet.');
INSERT INTO tb_product (name, price, description) VALUES ('Macbook Pro', 1250.0, 'Lorem ipsum dolor sit amet.');
INSERT INTO tb_product (name, price, description) VALUES ('PC Gamer', 1200.0, 'Lorem ipsum dolor sit amet.');
INSERT INTO tb_product (name, price, description) VALUES ('Rails for Dummies', 100.99, 'Lorem ipsum dolor sit amet.');
INSERT INTO tb_product (name, price, description) VALUES ('PC Gamer Ex', 1350.0, 'Lorem ipsum dolor sit amet.');
INSERT INTO tb_product (name, price, description) VALUES ('PC Gamer X', 1350.0, 'Lorem ipsum dolor sit amet.');
INSERT INTO tb_product (name, price, description) VALUES ('PC Gamer Alfa', 1850.0, 'Lorem ipsum dolor sit amet.');
INSERT INTO tb_product (name, price, description) VALUES ('PC Gamer Tera', 1950.0, 'Lorem ipsum dolor sit amet.');
INSERT INTO tb_product_category (product_id, category_id) VALUES (1, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 1);

