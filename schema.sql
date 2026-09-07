-- Vehicle Rental Management System - Database Schema
-- MySQL 8.x
-- Creates rental_db with all tables and relationships needed by the Java project.

CREATE DATABASE IF NOT EXISTS rental_db;
USE rental_db;

-- ------------------------------------------------------------
-- users: one table for both Admin and Customer (stores role).
-- email and address are stored here because the Java Customer model has them.
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(255),
    license_number VARCHAR(50),
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER'
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- vehicles: vehicle_type = CAR | MOTORCYCLE | TRUCK
-- status = AVAILABLE | RENTED | MAINTENANCE
-- year is stored (the Java Vehicle model keeps it).
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS vehicles (
    vehicle_id INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    vehicle_type VARCHAR(20) NOT NULL,
    year INT,
    rental_rate_per_day DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE'
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- rentals: rental_status = ACTIVE | COMPLETED | CANCELLED
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS rentals (
    rental_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    vehicle_id INT NOT NULL,
    rental_date DATE NOT NULL,
    return_date DATE NOT NULL,
    total_cost DECIMAL(10,2) NOT NULL,
    rental_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT fk_rentals_user FOREIGN KEY (user_id)
        REFERENCES users(user_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_rentals_vehicle FOREIGN KEY (vehicle_id)
        REFERENCES vehicles(vehicle_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- payments: payment_method = CASH | CARD
-- payment_status = PAID | FAILED | REFUNDED
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    rental_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATE NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PAID',
    CONSTRAINT fk_payments_rental FOREIGN KEY (rental_id)
        REFERENCES rentals(rental_id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ------------------------------------------------------------
-- Default admin account (matches the old in-memory admin:
-- username=admin, password=jamal000)
-- ------------------------------------------------------------
INSERT INTO users (username, password, full_name, phone, license_number, role)
VALUES ('admin', 'jamal000', 'System Admin', '77044825500', NULL, 'ADMIN')
ON DUPLICATE KEY UPDATE username = username;