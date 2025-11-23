-- Script para crear las tablas principales del sistema de firma biométrica (adaptado de Firebird a MySQL)

CREATE DATABASE IF NOT EXISTS bd_topaz CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_topaz;

-- Tabla de pacientes
CREATE TABLE IF NOT EXISTS patients (
    dni_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    left_handed SMALLINT,
    prof_id VARCHAR(13),
    fingerprint_img LONGBLOB,
    fingerprint_template LONGBLOB
);

-- Tabla de ingresos/firmas biométricas
CREATE TABLE IF NOT EXISTS entry_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_dni_id VARCHAR(20) NOT NULL,
    visit_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    patient_img LONGBLOB,
    signature_img LONGBLOB,
    signature_jpgimg LONGBLOB,
    signature_img2 LONGBLOB,
    signature LONGBLOB,
    FOREIGN KEY (patient_dni_id) REFERENCES patients(dni_id)
);

-- Índices para búsquedas rápidas
CREATE INDEX idx_entry_patient_dni ON entry_data(patient_dni_id);
