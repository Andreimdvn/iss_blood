-- DROP DATABASE IF EXISTS iss_blood;
-- CREATE DATABASE iss_blood;

USE iss_blood;

DROP TABLE IF EXISTS Medic;
DROP TABLE IF EXISTS Staff_recoltare;

DROP TABLE IF EXISTS Sange_prelucrat;
DROP TABLE IF EXISTS Sange_brut;
DROP TABLE IF EXISTS Analize;

DROP TABLE IF EXISTS Donator;
DROP TABLE IF EXISTS Staff_transfuzii;
DROP TABLE IF EXISTS Locatie;
DROP TABLE IF EXISTS Localitate;
DROP TABLE IF EXISTS Judet;
DROP TABLE IF EXISTS User;


CREATE TABLE User(
	id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL);
    
CREATE TABLE Judet(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nume VARCHAR(50) NOT NULL);

CREATE TABLE Localitate(
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_judet INT,
    nume VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_judet) REFERENCES Judet(id));

CREATE TABLE Donator(
	id_user INT PRIMARY KEY REFERENCES User(id),
    prenume VARCHAR(50) NOT NULL,
    nume VARCHAR(50) NOT NULL,
    cnp CHAR(13) NOT NULL,
	id_domiciliu INT,
    adresa_domiciliu VARCHAR(100) NOT NULL,
    data_nasterii DATE NOT NULL,
    telefon VARCHAR(20) NOT NULL,
    id_localitate_resedinta INT,
    adresa_resedinta VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_domiciliu) REFERENCES Localitate(id),
    FOREIGN KEY (id_localitate_resedinta) REFERENCES Localitate(id));

CREATE TABLE Locatie(
	id INT PRIMARY KEY AUTO_INCREMENT,
    nume VARCHAR(50) NOT NULL,
    adresa VARCHAR(100) NOT NULL,
    id_localitate INT,
    tip_locatie TINYINT NOT NULL,
    FOREIGN KEY (id_localitate) REFERENCES Localitate(id));

CREATE TABLE Staff_transfuzii(
	id_user INT PRIMARY KEY,
    id_locatie INT,
    telefon VARCHAR(20) NOT NULL,
    prenume VARCHAR(50) NOT NULL,
    nume VARCHAR(50) NOT NULL,
    cnp CHAR(13) NOT NULL,
    FOREIGN KEY (id_user) REFERENCES User(id),
    FOREIGN KEY (id_locatie) REFERENCES Locatie(id));

CREATE TABLE Staff_recoltare(
	id_user INT,
    id_locatie INT,
    prenume VARCHAR(50) NOT NULL,
    nume VARCHAR(50) NOT NULL,
    cnp CHAR(13) NOT NULL,
    FOREIGN KEY (id_user) REFERENCES User(id),
    FOREIGN KEY (id_locatie) REFERENCES Locatie(id));

CREATE TABLE Medic(
	id_user INT,
    id_locatie INT,
    prenume VARCHAR(50) NOT NULL,
    nume VARCHAR(50) NOT NULL,
    cnp CHAR(13) NOT NULL,
    telefon VARCHAR(20) NOT NULL,
    specializare VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_user) REFERENCES User(id),
    FOREIGN KEY (id_locatie) REFERENCES Locatie(id));
    
CREATE TABLE Analize(
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_staff_transfuzii INT,
    FOREIGN KEY (id_staff_transfuzii) REFERENCES Staff_transfuzii(id_user));

CREATE TABLE Sange_brut(
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_donator INT,
    id_locatie_recoltare INT,
    data_donare DATE NOT NULL,
    id_analize INT,
    stadiu ENUM('Recoltata', 'Analizata', 'Impartita', 'Aruncata') NOT NULL,
    id_locatie_curenta INT,
    FOREIGN KEY (id_donator) REFERENCES Donator(id_user),
    FOREIGN KEY (id_locatie_recoltare) REFERENCES Locatie(id),
    FOREIGN KEY (id_analize) REFERENCES Analize(id),
    FOREIGN KEY (id_locatie_curenta) REFERENCES Locatie(id));

CREATE TABLE Sange_prelucrat(
	id INT PRIMARY KEY AUTO_INCREMENT,
    id_sange_brut INT,
    tip ENUM('Plasma', 'Trombocite', 'Globule rosii') NOT NULL,
    id_locatie INT,
    gramaj FLOAT NOT NULL,
    status ENUM('DEPOZITAT', 'FOLOSIT', 'EXPIRAT'),
    FOREIGN KEY (id_sange_brut) REFERENCES Sange_brut(id),
    FOREIGN KEY (id_locatie) REFERENCES Locatie(id));