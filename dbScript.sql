-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema DSS
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `DSS` ;

CREATE USER IF NOT EXISTS 'user'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON DSS.* TO 'user'@'localhost';
FLUSH PRIVILEGES;

-- -----------------------------------------------------
-- Schema DSS
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `DSS` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `DSS` ;

-- -----------------------------------------------------
-- Table `DSS`.`Utilizadores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Utilizadores` (
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `passwd` VARCHAR(45) NULL DEFAULT NULL,
  `admin` TINYINT(4) NOT NULL,
  `login` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `DSS`.`Categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Categoria` (
  `idCategoria` INT NOT NULL,
  `designacao` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCategoria`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Media`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Media` (
  `name` VARCHAR(45) NOT NULL,
  `path` VARCHAR(45) NOT NULL,
  `owner` VARCHAR(45) NOT NULL,
  `album` VARCHAR(45) NULL DEFAULT NULL,
  `artista` VARCHAR(45) NULL DEFAULT NULL,
  `faixa` INT(11) NULL DEFAULT NULL,
  `release_date` DATE NOT NULL,
  `serie_name` VARCHAR(45) NULL DEFAULT NULL,
  `season` INT(11) NULL DEFAULT NULL,
  `episode` INT(11) NULL DEFAULT NULL,
  `categoria` INT NULL DEFAULT NULL,
  PRIMARY KEY (`name`),
  INDEX `fk_Media_Utilizadores_idx` (`owner` ASC) VISIBLE,
  INDEX `fk_Media_Categoria1_idx` (`categoria` ASC) VISIBLE,
  CONSTRAINT `fk_Media_Utilizadores`
    FOREIGN KEY (`owner`)
    REFERENCES `DSS`.`Utilizadores` (`email`),
  CONSTRAINT `fk_Media_Categoria1`
    FOREIGN KEY (`categoria`)
    REFERENCES `DSS`.`Categoria` (`idCategoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `DSS`.`Playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Playlist` (
  `Utilizadores_email` VARCHAR(45) NOT NULL,
  `Media_name` VARCHAR(45) NOT NULL,
  `playlist_id` INT(11) NOT NULL,
  `is_shared` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`Utilizadores_email`, `Media_name`),
  INDEX `fk_Utilizadores_has_Media_Media1_idx` (`Media_name` ASC) VISIBLE,
  INDEX `fk_Utilizadores_has_Media_Utilizadores1_idx` (`Utilizadores_email` ASC) VISIBLE,
  CONSTRAINT `fk_Utilizadores_has_Media_Media1`
    FOREIGN KEY (`Media_name`)
    REFERENCES `DSS`.`Media` (`name`),
  CONSTRAINT `fk_Utilizadores_has_Media_Utilizadores1`
    FOREIGN KEY (`Utilizadores_email`)
    REFERENCES `DSS`.`Utilizadores` (`email`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;


-- -----------------------------------------------------
-- Table `DSS`.`Amigos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Amigos` (
  `amigo` VARCHAR(45) NOT NULL,
  `amigo1` VARCHAR(45) NOT NULL,
  `pending` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`amigo`, `amigo1`),
  INDEX `fk_Utilizadores_has_Utilizadores_Utilizadores2_idx` (`amigo1` ASC) VISIBLE,
  INDEX `fk_Utilizadores_has_Utilizadores_Utilizadores1_idx` (`amigo` ASC) VISIBLE,
  CONSTRAINT `fk_Utilizadores_has_Utilizadores_Utilizadores1`
    FOREIGN KEY (`amigo`)
    REFERENCES `DSS`.`Utilizadores` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Utilizadores_has_Utilizadores_Utilizadores2`
    FOREIGN KEY (`amigo1`)
    REFERENCES `DSS`.`Utilizadores` (`email`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_unicode_ci;

INSERT INTO DSS.Utilizadores (email, name, passwd, admin) VALUES ('admin', 'admin', 'admin', TRUE);

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
