-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 06, 2021 at 06:14 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_sipulsa`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id_customer` int(11) NOT NULL,
  `noTelp` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `pulsa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id_customer`, `noTelp`, `username`, `password`, `pulsa`) VALUES
(1, '087654321', 'raynold', 'raynold', 65000);

-- --------------------------------------------------------

--
-- Table structure for table `pulsa`
--

CREATE TABLE `pulsa` (
  `id_pulsa` int(11) NOT NULL,
  `nominal_pulsa` int(11) NOT NULL,
  `harga_pulsa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pulsa`
--

INSERT INTO `pulsa` (`id_pulsa`, `nominal_pulsa`, `harga_pulsa`) VALUES
(1, 10000, 10000),
(2, 25000, 27000),
(3, 45000, 50000);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_pulsa`
--

CREATE TABLE `transaksi_pulsa` (
  `id_transpulsa` int(11) NOT NULL,
  `id_pulsa` int(11) NOT NULL,
  `tgl_transaksi` datetime NOT NULL,
  `id_customer` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi_pulsa`
--

INSERT INTO `transaksi_pulsa` (`id_transpulsa`, `id_pulsa`, `tgl_transaksi`, `id_customer`) VALUES
(14, 3, '2021-02-06 10:09:28', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_admin`
--

CREATE TABLE `user_admin` (
  `id_admin` int(11) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_admin`
--

INSERT INTO `user_admin` (`id_admin`, `username`, `password`) VALUES
(1, 'admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id_customer`);

--
-- Indexes for table `pulsa`
--
ALTER TABLE `pulsa`
  ADD PRIMARY KEY (`id_pulsa`);

--
-- Indexes for table `transaksi_pulsa`
--
ALTER TABLE `transaksi_pulsa`
  ADD PRIMARY KEY (`id_transpulsa`),
  ADD KEY `id_customer` (`id_customer`),
  ADD KEY `id_pulsa` (`id_pulsa`);

--
-- Indexes for table `user_admin`
--
ALTER TABLE `user_admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `pulsa`
--
ALTER TABLE `pulsa`
  MODIFY `id_pulsa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `transaksi_pulsa`
--
ALTER TABLE `transaksi_pulsa`
  MODIFY `id_transpulsa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaksi_pulsa`
--
ALTER TABLE `transaksi_pulsa`
  ADD CONSTRAINT `transaksi_pulsa_ibfk_1` FOREIGN KEY (`id_customer`) REFERENCES `customer` (`id_customer`),
  ADD CONSTRAINT `transaksi_pulsa_ibfk_2` FOREIGN KEY (`id_pulsa`) REFERENCES `pulsa` (`id_pulsa`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
