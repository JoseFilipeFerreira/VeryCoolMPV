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
  `email` VARCHAR(255) NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `passwd` VARCHAR(255) NULL DEFAULT NULL,
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
  `designacao` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idCategoria`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `DSS`.`Media`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `DSS`.`Media` (
  `name` VARCHAR(255) NOT NULL,
  `path` VARCHAR(300) NOT NULL,
  `owner` VARCHAR(255) NOT NULL,
  `edited_by` VARCHAR(255) NOT NULL,
  `album` VARCHAR(255) NULL DEFAULT NULL,
  `artista` VARCHAR(255) NULL DEFAULT NULL,
  `faixa` INT(11) NULL DEFAULT NULL,
  `release_date` DATE NOT NULL,
  `serie_name` VARCHAR(255) NULL DEFAULT NULL,
  `season` INT(11) NULL DEFAULT NULL,
  `episode` INT(11) NULL DEFAULT NULL,
  `categoria` INT NULL DEFAULT NULL,
  PRIMARY KEY (`name`, `edited_by`),
  INDEX `fk_Media_Utilizadores_idx` (`owner` ASC) VISIBLE,
  INDEX `fk_Media_Utilizadores1_idx` (`edited_by` ASC) VISIBLE,
  INDEX `fk_Media_Categoria1_idx` (`categoria` ASC) VISIBLE,
  CONSTRAINT `fk_Media_Utilizadores`
    FOREIGN KEY (`owner`)
    REFERENCES `DSS`.`Utilizadores` (`email`),
CONSTRAINT `fk_Media_Utilizadores1`
    FOREIGN KEY (`edited_by`)
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
  `Utilizadores_email` VARCHAR(255) NOT NULL,
  `Media_name` VARCHAR(255) NOT NULL,
  `playlist_id` INT(11) NOT NULL,
  `is_shared` TINYINT(4) NOT NULL DEFAULT 0,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`Utilizadores_email`, `Media_name`, `playlist_id`),
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
  `amigo` VARCHAR(255) NOT NULL,
  `amigo1` VARCHAR(255) NOT NULL,
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

Insert into DSS.Categoria Values (0, 'Blues');
Insert into DSS.Categoria Values (1, 'Classic rock');
Insert into DSS.Categoria Values (2, 'Country');
Insert into DSS.Categoria Values (3, 'Dance');
Insert into DSS.Categoria Values (4, 'Disco');
Insert into DSS.Categoria Values (5, 'Funk');
Insert into DSS.Categoria Values (6, 'Grunge');
Insert into DSS.Categoria Values (7, 'Hip-Hop');
Insert into DSS.Categoria Values (8, 'Jazz');
Insert into DSS.Categoria Values (9, 'Metal');
Insert into DSS.Categoria Values (10, 'New Age');
Insert into DSS.Categoria Values (11, 'Oldies');
Insert into DSS.Categoria Values (12, 'Other');
Insert into DSS.Categoria Values (13, 'Pop');
Insert into DSS.Categoria Values (14, 'Rhythm and Blues');
Insert into DSS.Categoria Values (15, 'Rap');
Insert into DSS.Categoria Values (16, 'Reggae');
Insert into DSS.Categoria Values (17, 'Rock');
Insert into DSS.Categoria Values (18, 'Techno');
Insert into DSS.Categoria Values (19, 'Industrial');
Insert into DSS.Categoria Values (20, 'Alternative');
Insert into DSS.Categoria Values (21, 'Ska');
Insert into DSS.Categoria Values (22, 'Death metal');
Insert into DSS.Categoria Values (23, 'Pranks');
Insert into DSS.Categoria Values (24, 'Soundtrack');
Insert into DSS.Categoria Values (25, 'Euro-Techno');
Insert into DSS.Categoria Values (26, 'Ambient');
Insert into DSS.Categoria Values (27, 'Trip-Hop');
Insert into DSS.Categoria Values (28, 'Vocal');
Insert into DSS.Categoria Values (29, 'Jazz & Funk');
Insert into DSS.Categoria Values (30, 'Fusion');
Insert into DSS.Categoria Values (31, 'Trance');
Insert into DSS.Categoria Values (32, 'Classical');
Insert into DSS.Categoria Values (33, 'Instrumental');
Insert into DSS.Categoria Values (34, 'Acid');
Insert into DSS.Categoria Values (35, 'House');
Insert into DSS.Categoria Values (36, 'Game');
Insert into DSS.Categoria Values (37, 'Sound clip');
Insert into DSS.Categoria Values (38, 'Gospel');
Insert into DSS.Categoria Values (39, 'Noise');
Insert into DSS.Categoria Values (40, 'Alternative Rock');
Insert into DSS.Categoria Values (41, 'Bass');
Insert into DSS.Categoria Values (42, 'Soul');
Insert into DSS.Categoria Values (43, 'Punk');
Insert into DSS.Categoria Values (44, 'Space');
Insert into DSS.Categoria Values (45, 'Meditative');
Insert into DSS.Categoria Values (46, 'Instrumental Pop');
Insert into DSS.Categoria Values (47, 'Instrumental Rock');
Insert into DSS.Categoria Values (48, 'Ethnic');
Insert into DSS.Categoria Values (49, 'Gothic');
Insert into DSS.Categoria Values (50, 'Darkwave');
Insert into DSS.Categoria Values (51, 'Techno-Industrial');
Insert into DSS.Categoria Values (52, 'Electronic');
Insert into DSS.Categoria Values (53, 'Pop-Folk');
Insert into DSS.Categoria Values (54, 'Eurodance');
Insert into DSS.Categoria Values (55, 'Dream');
Insert into DSS.Categoria Values (56, 'Southern Rock');
Insert into DSS.Categoria Values (57, 'Comedy');
Insert into DSS.Categoria Values (58, 'Cult');
Insert into DSS.Categoria Values (59, 'Gangsta');
Insert into DSS.Categoria Values (60, 'Top 40');
Insert into DSS.Categoria Values (61, 'Christian Rap');
Insert into DSS.Categoria Values (62, 'Pop/Funk');
Insert into DSS.Categoria Values (63, 'Jungle');
Insert into DSS.Categoria Values (64, 'Native US');
Insert into DSS.Categoria Values (65, 'Cabaret');
Insert into DSS.Categoria Values (66, 'New Wave');
Insert into DSS.Categoria Values (67, 'Psychedelic');
Insert into DSS.Categoria Values (68, 'Rave');
Insert into DSS.Categoria Values (69, 'Show tunes');
Insert into DSS.Categoria Values (70, 'Trailer');
Insert into DSS.Categoria Values (71, 'Lo-Fi');
Insert into DSS.Categoria Values (72, 'Tribal');
Insert into DSS.Categoria Values (73, 'Acid Punk');
Insert into DSS.Categoria Values (74, 'Acid Jazz');
Insert into DSS.Categoria Values (75, 'Polka');
Insert into DSS.Categoria Values (76, 'Retro');
Insert into DSS.Categoria Values (77, 'Musical');
Insert into DSS.Categoria Values (78, 'Rock ’n’ Roll');
Insert into DSS.Categoria Values (79, 'Hard Rock');
Insert into DSS.Categoria Values (80, 'Folk');
Insert into DSS.Categoria Values (81, 'Folk-Rock');
Insert into DSS.Categoria Values (82, 'National Folk');
Insert into DSS.Categoria Values (83, 'Swing');
Insert into DSS.Categoria Values (84, 'Fast Fusion');
Insert into DSS.Categoria Values (85, 'Bebop');
Insert into DSS.Categoria Values (86, 'Latin');
Insert into DSS.Categoria Values (87, 'Revival');
Insert into DSS.Categoria Values (88, 'Celtic');
Insert into DSS.Categoria Values (89, 'Bluegrass');
Insert into DSS.Categoria Values (90, 'Avantgarde');
Insert into DSS.Categoria Values (91, 'Gothic Rock');
Insert into DSS.Categoria Values (92, 'Progressive Rock');
Insert into DSS.Categoria Values (93, 'Psychedelic Rock');
Insert into DSS.Categoria Values (94, 'Symphonic Rock');
Insert into DSS.Categoria Values (95, 'Slow rock');
Insert into DSS.Categoria Values (96, 'Big Band');
Insert into DSS.Categoria Values (97, 'Chorus');
Insert into DSS.Categoria Values (98, 'Easy Listening');
Insert into DSS.Categoria Values (99, 'Acoustic');
Insert into DSS.Categoria Values (100, 'Humour');
Insert into DSS.Categoria Values (101, 'Speech');
Insert into DSS.Categoria Values (102, 'Chanson');
Insert into DSS.Categoria Values (103, 'Opera');
Insert into DSS.Categoria Values (104, 'Chamber music');
Insert into DSS.Categoria Values (105, 'Sonata');
Insert into DSS.Categoria Values (106, 'Symphony');
Insert into DSS.Categoria Values (107, 'Booty bass');
Insert into DSS.Categoria Values (108, 'Primus');
Insert into DSS.Categoria Values (109, 'Porn groove');
Insert into DSS.Categoria Values (110, 'Satire');
Insert into DSS.Categoria Values (111, 'Slow jam');
Insert into DSS.Categoria Values (112, 'Club');
Insert into DSS.Categoria Values (113, 'Tango');
Insert into DSS.Categoria Values (114, 'Samba');
Insert into DSS.Categoria Values (115, 'Folklore');
Insert into DSS.Categoria Values (116, 'Ballad');
Insert into DSS.Categoria Values (117, 'Power ballad');
Insert into DSS.Categoria Values (118, 'Rhythmic Soul');
Insert into DSS.Categoria Values (119, 'Freestyle');
Insert into DSS.Categoria Values (120, 'Duet');
Insert into DSS.Categoria Values (121, 'Punk Rock');
Insert into DSS.Categoria Values (122, 'Drum solo');
Insert into DSS.Categoria Values (123, 'A cappella');
Insert into DSS.Categoria Values (124, 'Euro-House');
Insert into DSS.Categoria Values (125, 'Dance Hall');
Insert into DSS.Categoria Values (126, 'Goa');
Insert into DSS.Categoria Values (127, 'Drum & Bass');
Insert into DSS.Categoria Values (128, 'Club-House');
Insert into DSS.Categoria Values (129, 'Hardcore Techno');
Insert into DSS.Categoria Values (130, 'Terror');
Insert into DSS.Categoria Values (131, 'Indie');
Insert into DSS.Categoria Values (132, 'BritPop');
Insert into DSS.Categoria Values (133, 'Negerpunk');
Insert into DSS.Categoria Values (134, 'Polsk Punk');
Insert into DSS.Categoria Values (135, 'Beat');
Insert into DSS.Categoria Values (136, 'Christian Gangsta Rap');
Insert into DSS.Categoria Values (137, 'Heavy Metal');
Insert into DSS.Categoria Values (138, 'Black Metal');
Insert into DSS.Categoria Values (139, 'Crossover');
Insert into DSS.Categoria Values (140, 'Contemporary Christian');
Insert into DSS.Categoria Values (141, 'Christian rock');
Insert into DSS.Categoria Values (142, 'Merengue');
Insert into DSS.Categoria Values (143, 'Salsa');
Insert into DSS.Categoria Values (144, 'Thrash Metal');
Insert into DSS.Categoria Values (145, 'Anime');
Insert into DSS.Categoria Values (146, 'Jpop');
Insert into DSS.Categoria Values (147, 'Synthpop');
Insert into DSS.Categoria Values (148, 'Abstract');
Insert into DSS.Categoria Values (149, 'Art Rock');
Insert into DSS.Categoria Values (150, 'Baroque');
Insert into DSS.Categoria Values (151, 'Bhangra');
Insert into DSS.Categoria Values (152, 'Big beat');
Insert into DSS.Categoria Values (153, 'Breakbeat');
Insert into DSS.Categoria Values (154, 'Chillout');
Insert into DSS.Categoria Values (155, 'Downtempo');
Insert into DSS.Categoria Values (156, 'Dub');
Insert into DSS.Categoria Values (157, 'EBM');
Insert into DSS.Categoria Values (158, 'Eclectic');
Insert into DSS.Categoria Values (159, 'Electro');
Insert into DSS.Categoria Values (160, 'Electroclash');
Insert into DSS.Categoria Values (161, 'Emo');
Insert into DSS.Categoria Values (162, 'Experimental');
Insert into DSS.Categoria Values (163, 'Garage');
Insert into DSS.Categoria Values (164, 'Global');
Insert into DSS.Categoria Values (165, 'IDM');
Insert into DSS.Categoria Values (166, 'Illbient');
Insert into DSS.Categoria Values (167, 'Industro-Goth');
Insert into DSS.Categoria Values (168, 'Jam Band');
Insert into DSS.Categoria Values (169, 'Krautrock');
Insert into DSS.Categoria Values (170, 'Leftfield');
Insert into DSS.Categoria Values (171, 'Lounge');
Insert into DSS.Categoria Values (172, 'Math Rock');
Insert into DSS.Categoria Values (173, 'New Romantic');
Insert into DSS.Categoria Values (174, 'Nu-Breakz');
Insert into DSS.Categoria Values (175, 'Post-Punk');
Insert into DSS.Categoria Values (176, 'Post-Rock');
Insert into DSS.Categoria Values (177, 'Psytrance');
Insert into DSS.Categoria Values (178, 'Shoegaze');
Insert into DSS.Categoria Values (179, 'Space Rock');
Insert into DSS.Categoria Values (180, 'Trop Rock');
Insert into DSS.Categoria Values (181, 'World Music');
Insert into DSS.Categoria Values (182, 'Neoclassical');
Insert into DSS.Categoria Values (183, 'Audiobook');
Insert into DSS.Categoria Values (184, 'Audio theatre');
Insert into DSS.Categoria Values (185, 'Neue Deutsche Welle');
Insert into DSS.Categoria Values (186, 'Podcast');
Insert into DSS.Categoria Values (187, 'Indie-Rock');
Insert into DSS.Categoria Values (188, 'G-Funk');
Insert into DSS.Categoria Values (189, 'Dubstep');
Insert into DSS.Categoria Values (190, 'Garage Rock');
Insert into DSS.Categoria Values (191, 'Psybien');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
