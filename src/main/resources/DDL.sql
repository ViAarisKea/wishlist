CREATE DATABASE Wishlist;
USE Wishlist;

CREATE TABLE users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        first_name VARCHAR(25),
                        last_name VARCHAR(255),
                        age INT,
                        email VARCHAR(255) UNIQUE,
                        password VARCHAR(255),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE wishlists (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           user_id INT,
                           name VARCHAR(255),
                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE items (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       wishlist_id INT,
                       name VARCHAR(255),
                       description VARCHAR(255),
                       price DOUBLE,
                       link VARCHAR(255),
                       img_url VARCHAR(255),
                       FOREIGN KEY (wishlist_id) REFERENCES wishlists(id) ON DELETE CASCADE
);