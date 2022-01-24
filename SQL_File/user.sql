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
-- Table structure for table `Users`
--

CREATE TABLE IF NOT EXISTS `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(500) NOT NULL,
  `name` varchar(100) NOT NULL,
  `lname` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `status` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `token` (`token`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=95 ;

--
-- Dumping data for table `Users`
--

INSERT INTO `Users` (`id`, `token`, `name`, `lname`, `email`, `pass`, `status`) VALUES
(88, 'cDVFonckO0M:APA91bEdR5FkR0fNmI64SyAMJdLCbOpwc8NecnAmPsusainTJXV-njCsZKtJ3_MeG_hlbjP-Gd3X-WGmy1r4hescbd59lpuOtSdxLHg5h6t-kDldGP6KQsX6iXhqjl_Xr89RNvh7zAc8', 'Ershad', 'Raoufi', 'wave@gmail.com', 'abcde', 'waiting'),
(89, 'cBpvFGqSwCY:APA91bGy4FH-OVSqwHgAVIhSsOLV0emLeSv34s05bf-GuTY8K-KxLcaOso3OoyusHNv0C2JdnVXWwPQzG70NtgdAcZWNj-owFa60t_6bCUFdGK_66pvxF2ERpKyb1_Q9WedTTAJGs_39', 'Ali', 'Kia', 'wave', 'aaaaa', 'waiting'),
(90, 'dNjhnUHiQbU:APA91bHpadRbxGeTxKX2WltbLcJ8Lbh3doeVd57JhzB_bdCVGxK-uEhWIJzA544kcJdkoWuJwKOpQaarrkPnNeX_RYUWfy8yxgZF7ubWY1lIILO5qea7GqPf3TnO1ihF1KalCl4-ADpr', 'mohsen', 'Nasiri', 'viva@gmail.com', 'abcde', 'waiting'),
(91, 'cyIsR8f4wRA:APA91bF3PcJu84BKsfosyNYafW-Y7uiaEn4VGkvltr6sKwKYb6LNynr2-7X5E66titTn5a8x_k_3OzBuH4DXSNAJnZs9mk8ZmuUgMqmAotR1FqR9nx4UkxQk2KCnhT9yFgszLzPqLwEP', 'aa', 'bb', 'aa@bb@gmail.com', 'aaaaaaaa', 'waiting'),
(93, 'euLmXIkYg4Y:APA91bE0xSCj9jQYANnGDupQz8HXeZBM73PpaJXwYMkaE8AV9coVjS_NWtloqzlzutxSXDJhUtgd3UutpJ1hn9lCevLnV6ISQNfQz0-UwQxESPoxciwYYBmECzGr9fNL8DYoSQwvES4O', 'farhad', 'raoufi', 'farhad@gmail.com', 'abcde', 'waiting');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
