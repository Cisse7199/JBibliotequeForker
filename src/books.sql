-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Mer 16 Octobre 2024 à 01:38
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `biblio`
--

-- --------------------------------------------------------

--
-- Structure de la table `books`
--

CREATE TABLE IF NOT EXISTS `books` (
  `id_book` int(11) NOT NULL AUTO_INCREMENT,
  `titre` text NOT NULL,
  `auteur` text NOT NULL,
  `categorie` varchar(60) NOT NULL,
  `statut` varchar(30) NOT NULL,
  PRIMARY KEY (`id_book`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=59 ;

--
-- Contenu de la table `books`
--

INSERT INTO `books` (`id_book`, `titre`, `auteur`, `categorie`, `statut`) VALUES
(39, 'Le souffle des ancêtres', 'Pierre Claver Ilboudo', 'Roman', 'disponible'),
(40, 'Le Cri du silence', 'Fulgence Zoungrana', 'Essai', 'disponible'),
(41, 'La terre qui chante', 'Béatrice Dembélé', 'Poésie', 'disponible'),
(42, 'L''enfant noir', 'Camara Laye', 'Roman', 'emprunter'),
(43, 'Quand les cauris s''effondrent', 'Isidore Zongo', 'Roman', 'disponible'),
(44, 'Soleil des indépendances', 'Ahmadou Kourouma', 'Roman', 'emprunter'),
(45, 'Les tambours de la nuit', 'Thomas Sankara', 'Histoire', 'emprunter'),
(46, 'Le secret du baobab', 'Awa Koné', 'Conte', 'disponible'),
(47, 'La bataille de Ouagadougou', 'Moussa Diallo', 'Histoire', 'emprunter'),
(48, 'Dieu n''aime pas le mensonge', 'Jacqueline Ki-Zerbo', 'Roman', 'disponible'),
(49, 'L''épopée des moissons', 'Seydou Ouedraogo', 'Roman', 'emprunter'),
(50, 'Sous le manguier', 'Salif Sanfo', 'Poésie', 'disponible'),
(51, 'Le sacré baobab', 'Nathalie Soré', 'Conte', 'emprunter'),
(52, 'La route de Yennenga', 'Ibrahim Ouédraogo', 'Histoire', 'emprunter'),
(53, 'L''aventure ambiguë', 'Cheikh Hamidou Kane', 'Roman', 'disponible'),
(54, 'Une si longue lettre', 'Mariama Bâ', 'Roman', 'emprunter'),
(55, 'Le devoir de violence', 'Yambo Ouologuem', 'Roman', 'disponible'),
(56, 'Les impatientes', 'Djaïli Amadou Amal', 'Roman', 'disponible'),
(57, 'Le vieux nègre et la médaille', 'Ferdinand Oyono', 'Roman', 'emprunter'),
(58, 'Le sang des masques', 'Jean-Baptiste Bassolé', 'Roman', 'disponible');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
