DROP TABLE IF EXISTS products;

CREATE TABLE products (
                          id bigint NOT NULL AUTO_INCREMENT,
                          title varchar(100) NOT NULL,
                          description varchar(255) NOT NULL,
                          price decimal(10,2) NOT NULL,
                          PRIMARY KEY(id)
);

INSERT INTO products (title, description, price) VALUES
('Super monitor 21', 'descript monic 1', '500.00'),
('Puper monitor 19', 'descript monic 2', '505.00'),
('Superpuper monitor 23', 'descript monic 3', '503.50'),
('Mouse 1', 'descript mouse 1', '60.10'),
('Mouse 2', 'descript mouse 2', '61.20'),
('Mouse 3', 'descript mouse 3', '62.30'),
('KeyBoard 1', 'descrip keyboard 1', '80.15'),
('KeyBoard 2', 'descript keyboard 2', '83.25'),
('KeyBoard 3', 'descript keyboard 3', '82.45'),
('PC 1', 'descript pc 1', '1000.00'),
('PC 2', 'descript pc 2', '1050.30'),
('PC 3', 'descript pc 3', '1020.45'),
('Audio system 1', 'descript audio 1', '205.45'),
('Audio system 2', 'descript audio 2', '203.23'),
('Audio system 3', 'descript audio 3', '201.56'),
('Printer 1', 'descript printer 1', '503.12'),
('Printer 2', 'descript printer 2', '507.38'),
('Printer 3', 'descript printer 3', '506.27'),
('Lamp 1', 'descript lamp 1', '33.56'),
('Lamp 2', 'descript lamp 2', '34.74');

DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       id bigint NOT NULL AUTO_INCREMENT,
                       name varchar(32) NOT NULL,
                       password varchar(32) NOT NULL,
                       age int,
                       email varchar(100),
                       PRIMARY KEY(id)
);

INSERT INTO users (name, password, age, email) VALUES
('user1', 'qwerty1', '25', 'mail1@mail.ru'),
('user2', 'qwerty2', '23', 'mail2@mail.ru'),
('user3', 'qwerty3', '33', 'mail3@mail.ru'),
('user4', 'qwerty4', '35', 'mail4@mail.ru'),
('user5', 'qwerty5', '34', 'mail5@mail.ru'),
('user6', 'qwerty6', '37', 'mail6@mail.ru'),
('user7', 'qwerty7', '12', 'mail7@mail.ru'),
('user8', 'qwerty8', '25', 'mail8@mail.ru'),
('user9', 'qwerty9', '14', 'mail9@mail.ru'),
('user10', 'qwerty10', '57', 'mail10@mail.ru'),
('user11', 'qwerty11', '46', 'mail11@mail.ru'),
('user12', 'qwerty12', '47', 'mail12@mail.ru')
;
DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
                          id bigint NOT NULL AUTO_INCREMENT,
                          name varchar(100) NOT NULL
);

INSERT INTO roles (name) VALUES ('ROLE_SUPERADMIN','ROLE_ADMIN','ROLE_GUEST');
