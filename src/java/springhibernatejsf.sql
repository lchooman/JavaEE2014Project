-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 30, 2014 at 01:40 PM
-- Server version: 5.5.40-0ubuntu0.12.04.1
-- PHP Version: 5.3.10-1ubuntu3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `springhibernatejsf`
--
CREATE DATABASE IF NOT EXISTS `springhibernatejsf` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `springhibernatejsf`;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `email_id` varchar(50) NOT NULL,
  `contact_number` varchar(15) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_email_uk` (`email_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`customer_id`, `email_id`, `contact_number`, `address`) VALUES
(1, 'ana@california.com', '683-342-3454', 'Sunset Boulevard, 93453, CA, USA'),
(3, 'tikong@ottawa.com', '682-181-9281', '45A Gunshot Avenue, Parliament Hill, OW 23423, Canada'),
(4, 'tijoe@toronto.com', '323-231-1211', '400 Lawrence Avenue, TO 45345, Canada'),
(5, 'tijack@montreal.com', '514-234-2342', '90 Avenue des Ibis, Montreal 54625, Canada');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE IF NOT EXISTS `employee` (
  `employee_id` int(11) NOT NULL,
  `login_name` varchar(30) NOT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `employee_login_uk` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employee_id`, `login_name`) VALUES
(2, 'lewis.jakes');

-- --------------------------------------------------------

--
-- Table structure for table `hall`
--

CREATE TABLE IF NOT EXISTS `hall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `theater_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `seats_available` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hall_theater_name_uk` (`theater_id`,`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `hall`
--

INSERT INTO `hall` (`id`, `theater_id`, `name`, `seats_available`) VALUES
(1, 1, 'Floor 1 Hall A', 78),
(2, 1, 'Floor 1 Hall B', 68),
(3, 1, 'Floor 2 Hall A', 87),
(4, 2, 'Floor 3 Hall A', 73),
(5, 2, 'Floor 3 Hall B', 89);

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE IF NOT EXISTS `movie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `main_actors` varchar(200) DEFAULT NULL,
  `released_date` date DEFAULT NULL,
  `image_file` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `movie_title_uk` (`title`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`id`, `title`, `main_actors`, `released_date`, `image_file`) VALUES
(1, 'Star Trek Into Darkness', 'Chris Pine, Zachary Quinto, Zoe Saldana', '2013-05-16', '../images/star-trek-into-darkness.jpg'),
(2, 'James Bond - Skyfall', 'Daniel Craig', '2012-11-09', '../images/james-bond-skyfall.jpg'),
(3, 'Fast and Furious 6', 'Vin Diesel, Dwayne Johnson', '2013-05-24', '../images/fast-furious-6.jpg'),
(4, 'X-Men - First Class', 'James McAvoy, Michael Fassbender, Jennifer Lawrence', '2011-06-03', '../images/x-men-first-class.jpg'),
(5, 'The Chronicles of Narnia: The Voyage of the Dawn Treader', 'Ben Barnes, Skandar Keynes, Georgie Henley', '2010-12-10', '../images/narnia-voyage-dawn-trader.jpg'),
(6, 'Harry Potter and the Half-Blood Prince', 'Daniel Radcliffe, Emma Watson, Rupert Grint', '2009-07-15', '../images/harry-potter-half-blood-prince.jpg'),
(7, 'Iron Man 3', 'Robert Downey Jr., Guy Pearce, Gwyneth Paltrow ', '2013-05-03', '../images/iron-man-3.jpg'),
(8, 'Pacific Rim', 'Idris Elba, Charlie Hunnam, Rinko Kikuchi', '2013-07-12', '../images/pacific-rim.jpg'),
(9, 'Step Up Revolution', 'Kathryn McCormick, Ryan Guzman, Cleopatra Coleman', '2012-07-27', '../images/step-up-revolution.jpg'),
(10, 'Prometheus', 'Noomi Rapace, Michael Fassbender, Charlize Theron', '2012-06-08', '../images/prometheus.jpg'),
(11, 'Hancock', 'Will Smith, Charlize Theron, Jason Bateman', '2008-06-02', '../images/hancock.jpg'),
(12, 'Avatar', 'Sam Worthington, Zoe Saldana, Sigourney Weaver', '2009-12-18', '../images/avatar.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `movie_show`
--

CREATE TABLE IF NOT EXISTS `movie_show` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movie_id` int(11) NOT NULL,
  `hall_id` int(11) NOT NULL,
  `show_date` date NOT NULL,
  `seats_available` int(11) NOT NULL,
  `seats_booked` int(11) NOT NULL,
  `seats_balance` int(11) NOT NULL,
  `price` decimal(15,2) NOT NULL,
  `amount` decimal(15,2) NOT NULL,
  `show_time` time NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `movie_show_hall_date_time_uk` (`hall_id`,`show_date`,`show_time`),
  KEY `movie_show_movie_id` (`movie_id`),
  KEY `movie_show_date_time_idx` (`show_date`,`show_time`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=217 ;

--
-- Dumping data for table `movie_show`
--

INSERT INTO `movie_show` (`id`, `movie_id`, `hall_id`, `show_date`, `seats_available`, `seats_booked`, `seats_balance`, `price`, `amount`, `show_time`) VALUES
(1, 1, 1, '2014-11-01', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(2, 2, 2, '2014-10-01', 68, 0, 68, 10.00, 0.00, '13:00:00'),
(3, 3, 3, '2014-10-01', 87, 0, 87, 10.00, 0.00, '13:00:00'),
(4, 4, 4, '2014-11-01', 73, 9, 64, 15.00, 135.00, '13:00:00'),
(5, 5, 5, '2014-11-01', 89, 7, 82, 15.00, 105.00, '13:00:00'),
(6, 6, 1, '2014-11-01', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(7, 5, 2, '2014-11-01', 68, 2, 66, 12.00, 24.00, '19:00:00'),
(8, 4, 3, '2014-11-01', 87, 8, 79, 12.00, 96.00, '19:30:00'),
(9, 2, 4, '2014-11-01', 73, 6, 67, 20.00, 120.00, '19:30:00'),
(10, 1, 5, '2014-11-01', 89, 3, 86, 20.00, 60.00, '19:30:00'),
(11, 5, 1, '2014-11-01', 78, 46, 32, 12.00, 552.00, '22:00:00'),
(12, 3, 2, '2014-11-01', 68, 11, 57, 12.00, 132.00, '22:00:00'),
(13, 6, 4, '2014-11-01', 73, 3, 70, 20.00, 60.00, '22:00:00'),
(14, 3, 5, '2014-11-01', 89, 13, 76, 20.00, 260.00, '22:00:00'),
(15, 2, 1, '2014-11-02', 78, 10, 68, 10.00, 100.00, '13:00:00'),
(16, 5, 4, '2014-11-02', 73, 12, 61, 15.00, 180.00, '13:00:00'),
(17, 6, 5, '2014-11-02', 89, 3, 86, 15.00, 45.00, '13:00:00'),
(18, 1, 1, '2014-11-02', 78, 2, 76, 12.00, 24.00, '19:00:00'),
(19, 6, 2, '2014-11-02', 68, 15, 53, 12.00, 180.00, '19:00:00'),
(20, 5, 3, '2014-11-02', 87, 2, 85, 12.00, 24.00, '19:30:00'),
(21, 3, 4, '2014-11-02', 73, 5, 68, 20.00, 100.00, '19:30:00'),
(22, 2, 5, '2014-11-02', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(23, 6, 1, '2014-11-02', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(24, 4, 2, '2014-11-02', 68, 3, 65, 12.00, 36.00, '22:00:00'),
(25, 1, 4, '2014-11-02', 73, 3, 70, 20.00, 60.00, '22:00:00'),
(26, 4, 5, '2014-11-02', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(31, 1, 1, '2014-11-03', 78, 6, 72, 10.00, 60.00, '13:00:00'),
(32, 4, 4, '2014-11-03', 73, 6, 67, 15.00, 90.00, '13:00:00'),
(33, 5, 5, '2014-11-03', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(34, 6, 1, '2014-11-03', 78, 5, 73, 12.00, 60.00, '19:00:00'),
(35, 5, 2, '2014-11-03', 68, 2, 66, 12.00, 24.00, '19:00:00'),
(36, 4, 3, '2014-11-03', 87, 7, 80, 12.00, 84.00, '19:30:00'),
(37, 2, 4, '2014-11-03', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(38, 1, 5, '2014-11-03', 89, 2, 87, 20.00, 40.00, '19:30:00'),
(39, 5, 1, '2014-11-03', 78, 8, 70, 12.00, 96.00, '22:00:00'),
(40, 3, 2, '2014-11-03', 68, 3, 65, 12.00, 36.00, '22:00:00'),
(41, 6, 4, '2014-11-03', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(42, 3, 5, '2014-11-03', 89, 3, 86, 20.00, 60.00, '22:00:00'),
(46, 2, 1, '2014-11-04', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(47, 5, 4, '2014-11-04', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(48, 6, 5, '2014-11-04', 89, 10, 79, 15.00, 150.00, '13:00:00'),
(49, 1, 1, '2014-11-04', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(50, 6, 2, '2014-11-04', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(51, 5, 3, '2014-11-04', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(52, 3, 4, '2014-11-04', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(53, 2, 5, '2014-11-04', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(54, 6, 1, '2014-11-04', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(55, 4, 2, '2014-11-04', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(56, 1, 4, '2014-11-04', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(57, 4, 5, '2014-11-04', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(61, 1, 1, '2014-11-05', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(62, 4, 4, '2014-11-05', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(63, 5, 5, '2014-11-05', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(64, 6, 1, '2014-11-05', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(65, 5, 2, '2014-11-05', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(66, 4, 3, '2014-11-05', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(67, 2, 4, '2014-11-05', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(68, 1, 5, '2014-11-05', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(69, 5, 1, '2014-11-05', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(70, 3, 2, '2014-11-05', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(71, 6, 4, '2014-11-05', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(72, 3, 5, '2014-11-05', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(76, 2, 1, '2014-11-06', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(77, 5, 4, '2014-11-06', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(78, 6, 5, '2014-11-06', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(79, 1, 1, '2014-11-06', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(80, 6, 2, '2014-11-06', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(81, 5, 3, '2014-11-06', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(82, 3, 4, '2014-11-06', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(83, 2, 5, '2014-11-06', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(84, 6, 1, '2014-11-06', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(85, 4, 2, '2014-11-06', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(86, 1, 4, '2014-11-06', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(87, 4, 5, '2014-11-06', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(91, 1, 1, '2014-11-07', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(92, 4, 4, '2014-11-07', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(93, 5, 5, '2014-11-07', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(94, 6, 1, '2014-11-07', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(95, 5, 2, '2014-11-07', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(96, 4, 3, '2014-11-07', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(97, 2, 4, '2014-11-07', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(98, 1, 5, '2014-11-07', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(99, 5, 1, '2014-11-07', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(100, 3, 2, '2014-11-07', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(101, 6, 4, '2014-11-07', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(102, 3, 5, '2014-11-07', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(106, 2, 1, '2014-11-08', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(107, 5, 4, '2014-11-08', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(108, 6, 5, '2014-11-08', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(109, 1, 1, '2014-11-08', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(110, 6, 2, '2014-11-08', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(111, 5, 3, '2014-11-08', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(112, 3, 4, '2014-11-08', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(113, 2, 5, '2014-11-08', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(114, 6, 1, '2014-11-08', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(115, 4, 2, '2014-11-08', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(116, 1, 4, '2014-11-08', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(117, 4, 5, '2014-11-08', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(119, 7, 1, '2014-11-09', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(120, 8, 2, '2014-10-09', 68, 0, 68, 10.00, 0.00, '13:00:00'),
(121, 9, 3, '2014-10-09', 87, 0, 87, 10.00, 0.00, '13:00:00'),
(122, 10, 4, '2014-11-09', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(123, 11, 5, '2014-11-09', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(124, 12, 1, '2014-11-09', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(125, 11, 2, '2014-11-09', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(126, 10, 3, '2014-11-09', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(127, 8, 4, '2014-11-09', 73, 5, 68, 20.00, 100.00, '19:30:00'),
(128, 7, 5, '2014-11-09', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(129, 11, 1, '2014-11-09', 78, 6, 72, 12.00, 72.00, '22:00:00'),
(130, 9, 2, '2014-11-09', 68, 2, 66, 12.00, 24.00, '22:00:00'),
(131, 12, 4, '2014-11-09', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(132, 9, 5, '2014-11-09', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(133, 8, 1, '2014-11-10', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(134, 11, 4, '2014-11-10', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(135, 12, 5, '2014-11-10', 89, 5, 84, 15.00, 75.00, '13:00:00'),
(136, 7, 1, '2014-11-10', 78, 12, 66, 12.00, 144.00, '19:00:00'),
(137, 12, 2, '2014-11-10', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(138, 11, 3, '2014-11-10', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(139, 9, 4, '2014-11-10', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(140, 8, 5, '2014-11-10', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(141, 12, 1, '2014-11-10', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(142, 10, 2, '2014-11-10', 68, 8, 60, 12.00, 96.00, '22:00:00'),
(143, 7, 4, '2014-11-10', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(144, 10, 5, '2014-11-10', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(145, 7, 1, '2014-11-11', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(146, 10, 4, '2014-11-11', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(147, 11, 5, '2014-11-11', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(148, 12, 1, '2014-11-11', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(149, 11, 2, '2014-11-11', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(150, 10, 3, '2014-11-11', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(151, 8, 4, '2014-11-11', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(152, 7, 5, '2014-11-11', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(153, 11, 1, '2014-11-11', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(154, 9, 2, '2014-11-11', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(155, 12, 4, '2014-11-11', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(156, 9, 5, '2014-11-11', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(157, 8, 1, '2014-11-12', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(158, 11, 4, '2014-11-12', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(159, 12, 5, '2014-11-12', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(160, 7, 1, '2014-11-12', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(161, 12, 2, '2014-11-12', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(162, 11, 3, '2014-11-12', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(163, 9, 4, '2014-11-12', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(164, 8, 5, '2014-11-12', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(165, 12, 1, '2014-11-12', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(166, 10, 2, '2014-11-12', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(167, 7, 4, '2014-11-12', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(168, 10, 5, '2014-11-12', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(169, 7, 1, '2014-11-13', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(170, 10, 4, '2014-11-13', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(171, 11, 5, '2014-11-13', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(172, 12, 1, '2014-11-13', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(173, 11, 2, '2014-11-13', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(174, 10, 3, '2014-11-13', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(175, 8, 4, '2014-11-13', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(176, 7, 5, '2014-11-13', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(177, 11, 1, '2014-11-13', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(178, 9, 2, '2014-11-13', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(179, 12, 4, '2014-11-13', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(180, 9, 5, '2014-11-13', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(181, 8, 1, '2014-11-14', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(182, 11, 4, '2014-11-14', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(183, 12, 5, '2014-11-14', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(184, 7, 1, '2014-11-14', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(185, 12, 2, '2014-11-14', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(186, 11, 3, '2014-11-14', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(187, 9, 4, '2014-11-14', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(188, 8, 5, '2014-11-14', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(189, 12, 1, '2014-11-14', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(190, 10, 2, '2014-11-14', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(191, 7, 4, '2014-11-14', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(192, 10, 5, '2014-11-14', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(193, 7, 1, '2014-11-15', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(194, 10, 4, '2014-11-15', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(195, 11, 5, '2014-11-15', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(196, 12, 1, '2014-11-15', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(197, 11, 2, '2014-11-15', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(198, 10, 3, '2014-11-15', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(199, 8, 4, '2014-11-15', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(200, 7, 5, '2014-11-15', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(201, 11, 1, '2014-11-15', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(202, 9, 2, '2014-11-15', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(203, 12, 4, '2014-11-15', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(204, 9, 5, '2014-11-15', 89, 0, 89, 20.00, 0.00, '22:00:00'),
(205, 8, 1, '2014-11-16', 78, 0, 78, 10.00, 0.00, '13:00:00'),
(206, 11, 4, '2014-11-16', 73, 0, 73, 15.00, 0.00, '13:00:00'),
(207, 12, 5, '2014-11-16', 89, 0, 89, 15.00, 0.00, '13:00:00'),
(208, 7, 1, '2014-11-16', 78, 0, 78, 12.00, 0.00, '19:00:00'),
(209, 12, 2, '2014-11-16', 68, 0, 68, 12.00, 0.00, '19:00:00'),
(210, 11, 3, '2014-11-16', 87, 0, 87, 12.00, 0.00, '19:30:00'),
(211, 9, 4, '2014-11-16', 73, 0, 73, 20.00, 0.00, '19:30:00'),
(212, 8, 5, '2014-11-16', 89, 0, 89, 20.00, 0.00, '19:30:00'),
(213, 12, 1, '2014-11-16', 78, 0, 78, 12.00, 0.00, '22:00:00'),
(214, 10, 2, '2014-11-16', 68, 0, 68, 12.00, 0.00, '22:00:00'),
(215, 7, 4, '2014-11-16', 73, 0, 73, 20.00, 0.00, '22:00:00'),
(216, 10, 5, '2014-11-16', 89, 0, 89, 20.00, 0.00, '22:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name_uk` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'customer'),
(2, 'employee');

-- --------------------------------------------------------

--
-- Table structure for table `show_booking`
--

CREATE TABLE IF NOT EXISTS `show_booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `show_id` int(11) NOT NULL,
  `date_booked` date NOT NULL,
  `seats_booked` int(11) NOT NULL,
  `card_number` varchar(19) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `ref_num` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `show_booking_customer_fk` (`customer_id`),
  KEY `show_booking_show_fk` (`show_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=53 ;

--
-- Dumping data for table `show_booking`
--

INSERT INTO `show_booking` (`id`, `show_id`, `date_booked`, `seats_booked`, `card_number`, `customer_id`, `ref_num`) VALUES
(1, 48, '2014-10-25', 2, '423423412341234324', 3, 599140315),
(2, 40, '2014-10-25', 3, '012345678901234', 3, 821118659),
(3, 48, '2014-10-25', 3, '423423412341234324', 3, 464136083),
(4, 48, '2014-10-25', 5, '0123456789012345', 3, 410705985),
(5, 16, '2014-10-25', 3, '953045923532042', 3, 423770265),
(6, 16, '2014-10-25', 9, '5693249824902384', 3, 178564143),
(7, 15, '2014-10-25', 5, '397819891231203', 3, 892036991),
(8, 34, '2014-10-25', 5, '77234234231231', 3, 386642841),
(9, 14, '2014-10-25', 3, '5534624567245672456', 3, 129465865),
(10, 25, '2014-10-25', 3, '5465456245624562', 3, 915626090),
(11, 12, '2014-10-25', 10, '53452345346134613', 3, 169388898),
(12, 38, '2014-10-26', 2, '3452342346262123', 3, 510556166),
(13, 32, '2014-10-26', 6, '7874678467846784678', 3, 386685952),
(14, 31, '2014-10-26', 6, '56856724562546256', 3, 787201815),
(15, 24, '2014-10-26', 3, '23423541234512345', 3, 276515883),
(16, 11, '2014-10-26', 3, '45734567346734', 3, 363077249),
(17, 36, '2014-10-26', 7, '5345234523452345', 3, 427656358),
(18, 35, '2014-10-26', 2, '562456245625624', 3, 351008141),
(19, 39, '2014-10-26', 8, '346245623423452345', 3, 593655997),
(20, 42, '2014-10-26', 3, '84645635672345723', 3, 753825427),
(21, 18, '2014-10-26', 2, '54673245625623453', 3, 849935041),
(22, 9, '2014-10-26', 2, '23123423542345234', 3, 787615396),
(23, 19, '2014-10-26', 11, '3413451345345', 3, 628484208),
(24, 21, '2014-10-26', 5, '4563545624562456', 3, 447792389),
(25, 12, '2014-10-26', 1, '21234242342342', 3, 500697802),
(26, 10, '2014-10-27', 3, '3464563456345', 3, 830339522),
(27, 5, '2014-10-27', 2, '452346236234623', 3, 914921339),
(28, 4, '2014-10-27', 2, '2323234235434234', 3, 455803402),
(29, 4, '2014-10-27', 2, '23424234534534', 3, 611011929),
(30, 9, '2014-10-27', 4, '23542353453425', 4, 473156374),
(31, 14, '2014-10-27', 3, '553345353453245256', 4, 351157720),
(32, 13, '2014-10-27', 1, '2123423452345345', 3, 594280350),
(33, 4, '2014-10-27', 2, '56735673567546756', 3, 497962031),
(34, 7, '2014-10-27', 2, '1235425467376747', 3, 291041483),
(35, 17, '2014-10-27', 3, '423523456236256', 5, 599461751),
(36, 8, '2014-10-27', 3, '784584784678467867', 5, 883936721),
(37, 20, '2014-10-27', 2, '54673735673567356', 5, 363419597),
(38, 14, '2014-10-27', 4, '256245624562456254', 5, 230072882),
(39, 11, '2014-10-27', 43, '34523465256245624', 4, 928647677),
(40, 4, '2014-10-27', 3, '3452346256254', 4, 943141501),
(41, 15, '2014-10-27', 5, '3453453673567564', 5, 947602382),
(42, 19, '2014-10-27', 4, '546256245625626', 3, 752085056),
(43, 8, '2014-10-27', 5, '467356847847846', 5, 572060849),
(44, 5, '2014-10-27', 5, '56735675673567356', 5, 215290883),
(45, 135, '2014-10-28', 5, '3567356735673567', 5, 129406794),
(46, 129, '2014-10-28', 6, '3567367367367', 5, 715476388),
(47, 127, '2014-10-28', 5, '957856784567356735', 5, 315036971),
(48, 136, '2014-10-28', 12, '678467835673456', 5, 261482535),
(49, 130, '2014-10-28', 2, '5673567356735673', 5, 405798399),
(50, 142, '2014-10-28', 8, '54673567835687356', 5, 126242183),
(51, 14, '2014-10-29', 3, '345662456245624', 4, 221949959),
(52, 13, '2014-10-30', 2, '547356735673567', 4, 407146298);

-- --------------------------------------------------------

--
-- Table structure for table `theater`
--

CREATE TABLE IF NOT EXISTS `theater` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(100) NOT NULL,
  `tel` varchar(15) NOT NULL,
  `fax` varchar(15) DEFAULT NULL,
  `email_id` varchar(50) DEFAULT NULL,
  `matinee_price` decimal(15,2) NOT NULL,
  `soiree_price` decimal(15,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `theater_name_uk` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `theater`
--

INSERT INTO `theater` (`id`, `name`, `address`, `tel`, `fax`, `email_id`, `matinee_price`, `soiree_price`) VALUES
(1, 'Sunset Boulevard Theater', '234, Sunset Boulevard, 35453, CA, USA', '534-252-9012', '534-954-1291', 'unlimited.sunset@california.com', 10.00, 12.00),
(2, 'Downtown Theater', '422, Downtown, LA 3424, CA, USA', '528-783-9102', '528-853-9120', 'unlimited.downtown@california.com', 15.00, 20.00);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `long_name` varchar(100) NOT NULL,
  `password` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `long_name`, `password`) VALUES
(1, 'Ana Romulus', 'e2d1810a8a281b0aa76aef68c1445973c6784915df8e24dab60b3a705deb04630c53d3921b37d068a00a23014fdb0756'),
(2, 'Lewis Jakes', '72aa7ba998900b37148445e91ef124988a24a69cff2b9c48eca0d8b216a4a4f0d454277841e324462232813083cbf035'),
(3, 'Mr Ti Kong', '3d6c75d617040e4f3f55eda9f246a5e9515fe8748c690c8700c2f90aa3e82f60f8b8be64d8f97b72667778624daae34c'),
(4, 'Ti Joe', 'be03c1483c3b7376681cebf7b3c86d8aa9c72f3d2f63a81f78fdd184cdee53a5dba4058fc0ed233b853a4701f4122b98'),
(5, 'Ti Jack', 'fcb4b7a0975072365b8393aa1cbfca23db54dd187f20045cc1214ffef94022b404aaa89dc1e874a3b147bb6db19f7752');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE IF NOT EXISTS `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role_uk` (`user_id`,`role_id`),
  KEY `user_role_role_fk` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`id`, `role_id`, `user_id`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5);

-- --------------------------------------------------------

--
-- Table structure for table `webpage`
--

CREATE TABLE IF NOT EXISTS `webpage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `description` varchar(50) NOT NULL,
  `login_flag` varchar(1) DEFAULT NULL,
  `menu_flag` varchar(1) NOT NULL,
  `menu_order` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `webpage_name_uk` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Dumping data for table `webpage`
--

INSERT INTO `webpage` (`id`, `name`, `description`, `login_flag`, `menu_flag`, `menu_order`) VALUES
(1, 'home', 'Home', NULL, 'Y', 1),
(2, 'customerLogin', 'Customer Login', 'N', 'Y', 70),
(3, 'aboutUs', 'About Us', NULL, 'Y', 40),
(4, 'contactUs', 'Contact Us', NULL, 'Y', 50),
(5, 'customerRegistration', 'Customer Registration', 'N', 'N', 60),
(6, 'employeeLogin', 'Employee Login', 'N', 'Y', 80),
(7, 'listMovies', 'Movie Shows', 'Y', 'Y', 10),
(8, 'bookShow', 'Book Show', 'Y', 'N', 0),
(9, 'dailySalesReport', 'Daily Sales Report', 'Y', 'Y', 20),
(10, 'showCollectionReport', 'Show Collection Report', 'Y', 'Y', 30),
(11, 'successRegistration', 'Successful Registration', 'N', 'N', 0),
(12, 'messages', 'Messages', NULL, 'N', 0),
(13, 'successBookShow', 'Successful Movie Show Booking', 'Y', 'N', 0),
(14, 'showCollectionShowsReport', 'Show Collection Report - Movie Shows', 'Y', 'N', 0),
(15, 'showCollectionShowReport', 'Show Collection Report - Movie Show', 'Y', 'N', 0);

-- --------------------------------------------------------

--
-- Table structure for table `webpage_role`
--

CREATE TABLE IF NOT EXISTS `webpage_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `webpage_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `webpage_role_uk` (`webpage_id`,`role_id`),
  KEY `webpage_role_role_fk` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `webpage_role`
--

INSERT INTO `webpage_role` (`id`, `role_id`, `webpage_id`) VALUES
(2, 1, 7),
(1, 1, 8),
(3, 2, 9),
(4, 2, 10),
(6, 1, 13),
(5, 2, 14),
(7, 2, 15);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `customer_user_fk` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `employee_user_fk` FOREIGN KEY (`employee_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `hall`
--
ALTER TABLE `hall`
  ADD CONSTRAINT `hall_theater_fk` FOREIGN KEY (`theater_id`) REFERENCES `theater` (`id`);

--
-- Constraints for table `movie_show`
--
ALTER TABLE `movie_show`
  ADD CONSTRAINT `movie_show_hall_id` FOREIGN KEY (`hall_id`) REFERENCES `hall` (`id`),
  ADD CONSTRAINT `movie_show_movie_id` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`);

--
-- Constraints for table `show_booking`
--
ALTER TABLE `show_booking`
  ADD CONSTRAINT `show_booking_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  ADD CONSTRAINT `show_booking_ibfk_2` FOREIGN KEY (`show_id`) REFERENCES `movie_show` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `user_role_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `user_role_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `webpage_role`
--
ALTER TABLE `webpage_role`
  ADD CONSTRAINT `webpage_role_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `webpage_role_webpage_fk` FOREIGN KEY (`webpage_id`) REFERENCES `webpage` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
