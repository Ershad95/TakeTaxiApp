-- phpMyAdmin SQL Dump
-- version 4.0.10.14
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Feb 11, 2017 at 07:19 AM
-- Server version: 10.1.18-MariaDB-cll-lve
-- PHP Version: 5.6.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cp25903_FCM`
--

-- --------------------------------------------------------

--
-- Table structure for table `Messages`
--

CREATE TABLE IF NOT EXISTS `Messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(500) DEFAULT 'Empty',
  `time` varchar(50) NOT NULL,
  `address` varchar(500) NOT NULL,
  `Destination` text NOT NULL,
  `telephone` varchar(15) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `token` (`token`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=210 ;

--
-- Dumping data for table `Messages`
--

INSERT INTO `Messages` (`id`, `token`, `time`, `address`, `Destination`, `telephone`, `name`) VALUES
(36, 'Empty', '2017-01-30 //12:58:51', 'Karaj', '', '09656122566', 'Alireza Raoufi'),
(128, 'Empty', '2017-02-02// 13:56:35', 'bdbddbd', '', '0964964', 'test'),
(133, 'Empty', '2017-02-03// 10:25:58', 'test', 'test', '865959', 'test'),
(137, 'Empty', '2017-02-03// 11:05:25', 'test3', 'test3', '844543', 'test3'),
(142, 'cBpvFGqSwCY:APA91bGy4FH-OVSqwHgAVIhSsOLV0emLeSv34s05bf-GuTY8K-KxLcaOso3OoyusHNv0C2JdnVXWwPQzG70NtgdAcZWNj-owFa60t_6bCUFdGK_66pvxF2ERpKyb1_Q9WedTTAJGs_39', '2017-02-07 //23:29:11', 'test4', 'test4', '83784', 'test4'),
(208, 'cBpvFGqSwCY:APA91bGy4FH-OVSqwHgAVIhSsOLV0emLeSv34s05bf-GuTY8K-KxLcaOso3OoyusHNv0C2JdnVXWwPQzG70NtgdAcZWNj-owFa60t_6bCUFdGK_66pvxF2ERpKyb1_Q9WedTTAJGs_39', '2017-02-08//00:25:51', 'asdsds', 'dfdfdf', '2733', 'sad'),
(209, 'Empty', '2017-03-12//20:11:21', 'Karaj', 'Tehran', '0921827396', 'mohsen');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
