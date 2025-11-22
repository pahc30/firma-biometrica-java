-- Script para crear la base de datos y tabla para el sistema de registro biométrico

CREATE DATABASE IF NOT EXISTS bd_topaz CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_topaz;

CREATE TABLE IF NOT EXISTS personas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(20) NOT NULL,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    firma LONGBLOB NOT NULL,
    huella LONGBLOB NOT NULL,
    foto LONGBLOB NOT NULL,
    fecha DATETIME NOT NULL
);

-- Índice para búsquedas rápidas por DNI
CREATE INDEX idx_dni ON personas(dni);
