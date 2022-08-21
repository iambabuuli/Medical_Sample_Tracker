-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Aug 21, 2022 at 05:47 AM
-- Server version: 10.3.36-MariaDB
-- PHP Version: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wenzotek_sample_tracker`
--

-- --------------------------------------------------------

--
-- Table structure for table `payment_of_account`
--

CREATE TABLE `payment_of_account` (
  `patient_id` varchar(50) NOT NULL,
  `national_id_number` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `title` varchar(20) NOT NULL,
  `postal_address` varchar(50) NOT NULL,
  `phone_number` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `employer` varchar(50) NOT NULL,
  `medical_aid_name` varchar(50) NOT NULL,
  `cash_receipt_number` varchar(50) NOT NULL,
  `date_payed` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `received_samples`
--

CREATE TABLE `received_samples` (
  `sample_id` varchar(50) NOT NULL,
  `patient_id` varchar(50) NOT NULL,
  `suspected_disease` varchar(50) NOT NULL,
  `sample_type` varchar(20) NOT NULL,
  `sample_status` varchar(20) NOT NULL,
  `results` varchar(20) NOT NULL DEFAULT 'Pending.',
  `lab_notes` text NOT NULL,
  `registered_by` varchar(50) NOT NULL,
  `received_by` varchar(50) NOT NULL,
  `registers_phone_number` varchar(50) NOT NULL,
  `receivers_phone_number` varchar(50) NOT NULL,
  `date_received` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `received_samples`
--

INSERT INTO `received_samples` (`sample_id`, `patient_id`, `suspected_disease`, `sample_type`, `sample_status`, `results`, `lab_notes`, `registered_by`, `received_by`, `registers_phone_number`, `receivers_phone_number`, `date_received`) VALUES
('111', '5779', 'Typhoid', 'Blood', 'Intact', 'Negative', 'Not infected', 'Edrine', 'Account', '0774263134', '0770000000', '2022-08-04 12:12:45');

-- --------------------------------------------------------

--
-- Table structure for table `registered_samples`
--

CREATE TABLE `registered_samples` (
  `sample_id` varchar(50) NOT NULL,
  `patient_id` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `date_of_birth` varchar(50) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `address` varchar(50) NOT NULL,
  `age` varchar(10) NOT NULL,
  `hospital_number` varchar(50) NOT NULL,
  `phone_number` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `hospital_patient` varchar(50) NOT NULL,
  `suspected_disease` varchar(50) NOT NULL,
  `sample_type` varchar(50) NOT NULL,
  `next_of_kin` varchar(50) NOT NULL,
  `clinical_information` text NOT NULL,
  `expected_date_of_return` varchar(50) NOT NULL,
  `is_sample_received` varchar(5) NOT NULL DEFAULT 'No',
  `results` varchar(20) NOT NULL DEFAULT 'Pending',
  `date_registered` timestamp NOT NULL DEFAULT current_timestamp(),
  `registered_by` varchar(100) NOT NULL,
  `paid` varchar(10) NOT NULL DEFAULT 'No',
  `received_by` varchar(50) NOT NULL,
  `lab_notes` text NOT NULL,
  `registers_phone_number` varchar(50) NOT NULL,
  `receivers_phone_number` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `registered_samples`
--

INSERT INTO `registered_samples` (`sample_id`, `patient_id`, `first_name`, `last_name`, `date_of_birth`, `gender`, `address`, `age`, `hospital_number`, `phone_number`, `email`, `hospital_patient`, `suspected_disease`, `sample_type`, `next_of_kin`, `clinical_information`, `expected_date_of_return`, `is_sample_received`, `results`, `date_registered`, `registered_by`, `paid`, `received_by`, `lab_notes`, `registers_phone_number`, `receivers_phone_number`) VALUES
('111', '5779', 'Matovu', 'Sunday', '2020-6-25', 'Male', 'Masaka', '22', '89653', '0702368951', 'matovu@gmail.com', 'Yes', 'Typhoid', 'Blood', 'Joshua', 'Treatment', '2020-6-23', 'Yes', 'Negative', '2020-06-02 21:05:39', 'Edrine', 'No', 'Account', 'Not infected', '0774263134', '0770000000'),
('2111', '4667', 'Muwonge', 'Fahad', '2020-6-14', 'Male', 'wakiso', '24', '2222', '0788569932', '', 'No', 'Malaria', 'Urine', 'Jeff', 'treatment', '2020-6-23', 'No', 'Pending', '2020-06-02 21:35:40', 'Joshua', 'No', '', '', '0772391345', ''),
('233345', '', '', '', '', '', '', '', '', '', '', 'Yes', 'Malaria ', 'Blood', 'John', '', '2022-8-6', 'No', 'Pending', '2022-08-05 17:30:29', 'Account', 'No', '', '', '0770000000', ''),
('3322', '3322', 'Atwiine', 'Wilson', '2020-6-16', 'Male', 'Ishaka', '24', '5598', '0774189562', 'atwiine@gmail.com', 'Yes', 'Hepatitis B', 'Urine', 'Nelson', 'treatment', '2020-6-17', 'No', 'Pending', '2020-06-02 21:31:29', 'Joshua', 'No', '', '', '0772391345', ''),
('333', '6789', 'Tumusiime', 'Joshua', '2020-6-24', 'Male', 'Kiwempe', '25', '89965', '0704949865', 'tumusiimejoshua47@gmail.com', 'No', 'Malaria', 'Blood', 'Joyce', 'Medicine', '2020-6-3', 'No', 'Pending', '2020-06-02 21:02:05', 'Edrine', 'No', '', '', '0774263134', ''),
('4444', '378890', 'Tukube ', 'Moses', '2020-6-4', 'Male', 'Salama', '20', '890743', '078966532', 'moses@gmail.com', 'No', 'Malaria', 'Blood', 'Jack', 'Medicine', '2020-6-2', 'No', 'Pending', '2020-06-02 21:00:01', 'Edrine', 'No', '', '', '0774263134', ''),
('5500', '5500', 'Lugemwa', 'Emmanuel', '2020-6-15', 'Male', 'Wakiso', '50', '055690', '0774859632', 'lugemwa@gmail.com', 'Yes', 'Cholera', 'Blood', 'Zack', 'treatment', '2020-6-23', 'No', 'Pending', '2020-06-02 21:26:38', 'Joshua', 'No', '', '', '0772391345', ''),
('5555', '47890', 'Mutebi ', 'Solomon', '2020-6-2', 'Male', 'Nabutiti', '23', '256390', '0705863291', 'solomon@gmail.com', 'Yes', 'Covid 19', 'Blood', 'James', 'Medical', '2020-6-2', 'No', 'Pending', '2020-06-02 20:57:29', 'Edrine', 'No', '', '', '0774263134', ''),
('6666', '4321', 'Luyombya', 'Edrine', '2020-6-16', 'Male', 'Kiwempe', '19', '47889', '0758963212', 'luyobya@gmail.com', 'Yes', 'HIV', 'Blood', 'Sunday', 'Medicine', '2020-6-24', 'No', 'Pending', '2020-06-02 21:07:39', 'Edrine', 'No', '', '', '0774263134', '');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone_number` varchar(50) NOT NULL,
  `password` text NOT NULL,
  `account_type` varchar(50) NOT NULL,
  `profile_picture_path` text NOT NULL,
  `profile_picture_address` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`first_name`, `last_name`, `email`, `phone_number`, `password`, `account_type`, `profile_picture_path`, `profile_picture_address`) VALUES
('Testing ', 'Account', 'test@email.com', '0770000000', '$2y$10$UF2KzEYglwR14NUxhvcX9OrpCJU6PcFeK7kM4m6TVl2cM6vVvlOx.', 'Administrator', 'profile_pictures/IMG_1659614543337', 'http://wenzotek.com/projects/api/sample_tracker_app/sample_tracker_app/profile_pictures/IMG_1659614543337'),
('Test', 'Account Two', 'testtwo@email.com', '0772222222', '$2y$10$3NqrixgpvJR4YzURod8BiOhRkkA/IaOVyYTFZXoFRvNAIU0OJT9lC', 'Administrator', 'profile_pictures/IMG_1659721311573', 'http://wenzotek.com/projects/api/sample_tracker_app/sample_tracker_app/profile_pictures/IMG_1659721311573');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `payment_of_account`
--
ALTER TABLE `payment_of_account`
  ADD PRIMARY KEY (`patient_id`);

--
-- Indexes for table `received_samples`
--
ALTER TABLE `received_samples`
  ADD PRIMARY KEY (`sample_id`);

--
-- Indexes for table `registered_samples`
--
ALTER TABLE `registered_samples`
  ADD PRIMARY KEY (`sample_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
