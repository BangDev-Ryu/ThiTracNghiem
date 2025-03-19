-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 19, 2025 at 10:17 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tracnghiem`
--

-- --------------------------------------------------------

--
-- Table structure for table `answer`
--

CREATE TABLE `answer` (
  `id` int(11) NOT NULL,
  `question_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `is_right` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `exam`
--

CREATE TABLE `exam` (
  `test_id` int(11) NOT NULL,
  `test_code` varchar(255) DEFAULT NULL,
  `ex_order` varchar(255) NOT NULL,
  `ex_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exam`
--

INSERT INTO `exam` (`test_id`, `test_code`, `ex_order`, `ex_code`) VALUES
(1, 'TST001', '1', 'EX001'),
(2, 'TST002', '2', 'EX002'),
(3, 'TST003', '1', 'EX003'),
(4, 'TST004', '3', 'EX004'),
(5, 'TST005', '2', 'EX005'),
(6, 'TST006', '1', 'EX006'),
(7, 'TST007', '2', 'EX007'),
(8, 'TST008', '3', 'EX008'),
(9, 'TST009', '1', 'EX009'),
(10, 'TST010', '2', 'EX010'),
(11, 'TST011', '3', 'EX011'),
(12, 'TST012', '1', 'EX012'),
(13, 'TST013', '2', 'EX013'),
(14, 'TST014', '3', 'EX014'),
(15, 'TST015', '1', 'EX015'),
(16, 'TST016', '2', 'EX016'),
(17, 'TST017', '1', 'EX017'),
(18, 'TST018', '3', 'EX018'),
(19, 'TST019', '2', 'EX019'),
(20, 'TST020', '1', 'EX020'),
(21, 'TST021', '3', 'EX021'),
(22, 'TST022', '1', 'EX022'),
(23, 'TST023', '2', 'EX023'),
(24, 'TST024', '3', 'EX024'),
(25, 'TST025', '1', 'EX025'),
(26, 'TST026', '2', 'EX026'),
(27, 'TST027', '3', 'EX027'),
(28, 'TST028', '1', 'EX028'),
(29, 'TST029', '2', 'EX029'),
(30, 'TST030', '1', 'EX030'),
(31, 'TST031', '3', 'EX031');

-- --------------------------------------------------------

--
-- Table structure for table `exam_detail`
--

CREATE TABLE `exam_detail` (
  `test_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `topic_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`id`, `topic_id`, `content`, `picture`, `level`, `status`) VALUES
(1, 1, 'Câu hỏi 1', NULL, 'Easy', 1),
(2, 1, 'Câu hỏi 2', NULL, 'Medium', 1),
(4, 2, 'Câu hỏi 4', NULL, 'Easy', 1),
(5, 3, 'Câu hỏi 5', NULL, 'Medium', 1);

-- --------------------------------------------------------

--
-- Table structure for table `result`
--

CREATE TABLE `result` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `mark` decimal(10,0) DEFAULT NULL,
  `date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `result`
--

INSERT INTO `result` (`id`, `user_id`, `test_id`, `mark`, `date`) VALUES
(1, 1, 1, 90, '2025-03-14 10:00:00'),
(2, 2, 1, 80, '2025-03-14 10:10:00'),
(3, 3, 1, 85, '2025-03-14 10:20:00'),
(4, 4, 1, 95, '2025-03-14 10:30:00'),
(5, 5, 1, 45, '2025-03-14 10:40:00'),
(6, 6, 1, 45, '2025-03-14 10:50:00'),
(7, 7, 2, 88, '2025-03-14 11:00:00'),
(8, 8, 2, 92, '2025-03-14 11:10:00'),
(9, 9, 2, 78, '2025-03-14 11:20:00'),
(10, 10, 2, 85, '2025-03-14 11:30:00'),
(11, 11, 3, 10, '2025-03-14 11:40:00'),
(12, 12, 3, 90, '2025-03-14 11:50:00'),
(13, 13, 3, 80, '2025-03-14 12:00:00'),
(14, 14, 3, 95, '2025-03-14 12:10:00'),
(15, 15, 4, 17, '2025-03-14 12:20:00'),
(16, 16, 4, 88, '2025-03-14 12:30:00'),
(17, 17, 4, 77, '2025-03-14 12:40:00'),
(18, 18, 4, 20, '2025-03-14 12:50:00'),
(19, 1, 1, 55, '2025-01-15 10:00:00'),
(19, 19, 5, 80, '2025-03-14 13:00:00'),
(20, 2, 2, 60, '2025-01-20 14:30:00'),
(21, 3, 3, 70, '2025-02-01 09:15:00'),
(22, 4, 4, 65, '2025-02-10 11:20:00'),
(23, 5, 5, 72, '2025-03-05 08:45:00'),
(24, 6, 6, 68, '2025-03-15 13:30:00'),
(25, 7, 7, 80, '2025-04-01 10:00:00'),
(26, 8, 8, 75, '2025-04-18 16:45:00'),
(27, 9, 9, 85, '2025-05-02 14:30:00'),
(28, 10, 10, 90, '2025-05-20 12:00:00'),
(29, 11, 11, 60, '2025-06-05 11:30:00'),
(30, 12, 12, 77, '2025-06-15 08:00:00'),
(31, 13, 13, 85, '2025-07-01 15:00:00'),
(32, 14, 14, 78, '2025-07-10 10:00:00'),
(33, 15, 15, 80, '2025-08-05 13:00:00'),
(34, 1, 16, 65, '2025-08-20 09:30:00'),
(35, 2, 1, 60, '2025-09-15 11:00:00'),
(36, 3, 2, 88, '2025-09-25 14:45:00'),
(37, 4, 3, 45, '2025-01-08 10:30:00'),
(38, 5, 4, 40, '2025-02-03 09:30:00'),
(39, 6, 5, 39, '2025-02-14 11:45:00'),
(40, 7, 6, 48, '2025-03-08 15:00:00'),
(41, 8, 7, 42, '2025-03-22 13:00:00'),
(42, 9, 8, 30, '2025-04-12 10:15:00'),
(43, 10, 9, 46, '2025-05-06 14:30:00'),
(44, 11, 10, 29, '2025-05-15 09:00:00'),
(45, 12, 11, 49, '2025-06-10 08:30:00'),
(46, 13, 12, 40, '2025-07-01 11:15:00'),
(47, 14, 13, 45, '2025-07-18 14:00:00'),
(48, 15, 14, 38, '2025-08-02 10:00:00'),
(49, 1, 15, 47, '2025-09-06 13:30:00'),
(50, 2, 16, 43, '2025-10-04 09:00:00'),
(51, 3, 1, 100, '2025-11-01 15:30:00');

-- --------------------------------------------------------

--
-- Table structure for table `result_detail`
--

CREATE TABLE `result_detail` (
  `id` int(11) NOT NULL,
  `answer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `id` int(11) NOT NULL,
  `test_code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `time` int(11) DEFAULT NULL,
  `limit` tinyint(4) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `test`
--

INSERT INTO `test` (`id`, `test_code`, `name`, `time`, `limit`, `date`, `status`) VALUES
(1, 'TST001', 'Test Example', 30, 5, '2025-03-16 10:00:00', 1),
(2, 'TST002', 'Test B', 45, 1, '2025-03-21 11:00:00', 1),
(3, 'TST003', 'Test C', 30, 2, '2025-03-22 09:00:00', 1),
(4, 'TST004', 'Test D', 120, 1, '2025-03-23 14:00:00', 1),
(5, 'TST005', 'Test E', 60, 2, '2025-03-24 13:00:00', 1),
(6, 'TST006', 'Test F', 90, 1, '2025-03-25 16:00:00', 1),
(7, 'TST007', 'Test G', 60, 2, '2025-03-26 10:00:00', 1),
(8, 'TST008', 'Test H', 45, 1, '2025-03-27 08:00:00', 0),
(9, 'TST009', 'Test I', 75, 2, '2025-03-28 12:00:00', 1),
(10, 'TST010', 'Test J', 100, 1, '2025-03-29 15:00:00', 1),
(11, 'TST011', 'Test K', 90, 2, '2025-03-30 09:00:00', 0),
(12, 'TST012', 'Test L', 60, 1, '2025-03-31 11:00:00', 1),
(13, 'TST013', 'Test M', 120, 1, '2025-04-01 14:00:00', 1),
(14, 'TST014', 'Test N', 45, 2, '2025-04-02 10:00:00', 1),
(15, 'TST015', 'Test O', 30, 1, '2025-04-03 16:00:00', 0),
(16, 'TST016', 'Test P', 60, 2, '2025-04-04 12:00:00', 1),
(17, 'TST017', 'Test Q', 45, 1, '2025-04-05 13:00:00', 1),
(18, 'TST018', 'Test R', 90, 1, '2025-04-06 10:00:00', 1),
(19, 'TST019', 'Test S', 60, 2, '2025-04-07 09:00:00', 0),
(20, 'TST020', 'Test T', 75, 1, '2025-04-08 11:00:00', 1),
(21, 'TST021', 'Test U', 45, 2, '2025-04-09 14:00:00', 1),
(22, 'TST022', 'Test V', 30, 1, '2025-04-10 15:00:00', 0),
(23, 'TST023', 'Test W', 120, 1, '2025-04-11 16:00:00', 1),
(24, 'TST024', 'Test X', 60, 2, '2025-04-12 13:00:00', 1),
(25, 'TST025', 'Test Y', 45, 1, '2025-04-13 14:00:00', 0),
(26, 'TST026', 'Test Z', 75, 2, '2025-04-14 11:00:00', 1),
(27, 'TST027', 'Test AA', 60, 1, '2025-04-15 10:00:00', 1),
(28, 'TST028', 'Test AB', 90, 2, '2025-04-16 09:00:00', 0),
(29, 'TST029', 'Test AC', 45, 1, '2025-04-17 12:00:00', 1),
(30, 'TST030', 'Test AD', 30, 1, '2025-04-18 08:00:00', 1),
(31, 'TST031', 'Test AE', 60, 2, '2025-04-19 13:00:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `test_structure`
--

CREATE TABLE `test_structure` (
  `test_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL,
  `num_easy` int(11) DEFAULT NULL,
  `num_medium` int(11) DEFAULT NULL,
  `num_hard` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `topic`
--

CREATE TABLE `topic` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `topic`
--

INSERT INTO `topic` (`id`, `name`, `parent`, `status`) VALUES
(1, 'Technology', 0, 1),
(2, 'Programming', 1, 1),
(3, 'Web Development', 2, 1),
(4, 'Mobile Development', 2, 1),
(5, 'Data Science', 24, 1),
(6, 'AI and Machine Learning', 5, 1),
(7, 'Cloud Computing', 1, 1),
(8, 'Cybersecurity', 1, 1),
(9, 'Software Engineering', 2, 1),
(10, 'Game Development', 2, 1),
(11, 'Science', NULL, 1),
(12, 'Physics', 11, 1),
(13, 'Chemistry', 11, 1),
(14, 'Biology', 11, 1),
(15, 'Mathematics', 11, 1),
(16, 'History', NULL, 1),
(17, 'World History', 16, 1),
(18, 'Art History', 16, 1),
(19, 'Philosophy', NULL, 1),
(20, 'Ethics', 19, 1),
(21, 'Python', 0, 1),
(22, 'C++', 2, 1),
(23, 'Java', 3, 1),
(24, 'C#', 6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `id_admin` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `password`, `fullname`, `id_admin`) VALUES
(1, 'user1', 'user1@example.com', 'password1', 'User One', 0),
(2, 'user2', 'user2@example.com', 'password2', 'User Two', 0),
(3, 'user3', 'user3@example.com', 'password3', 'User Three', 0),
(4, 'user4', 'user4@example.com', 'password4', 'User Four', 0),
(5, 'user5', 'user5@example.com', 'password5', 'User Five', 1),
(6, 'user6', 'user6@example.com', 'password6', 'User Six', 0),
(7, 'user7', 'user7@example.com', 'password7', 'User Seven', 0),
(8, 'user8', 'user8@example.com', 'password8', 'User Eight', 0),
(9, 'user9', 'user9@example.com', 'password9', 'User Nine', 0),
(10, 'user10', 'user10@example.com', 'password10', 'User Ten', 0),
(11, 'user11', 'user11@example.com', 'password11', 'User Eleven', 0),
(12, 'user12', 'user12@example.com', 'password12', 'User Twelve', 0),
(13, 'user13', 'user13@example.com', 'password13', 'User Thirteen', 1),
(14, 'user14', 'user14@example.com', 'password14', 'User Fourteen', 0),
(15, 'user15', 'user15@example.com', 'password15', 'User Fifteen', 0),
(16, 'user16', 'user16@example.com', 'password16', 'User Sixteen', 0),
(17, 'user17', 'user17@example.com', 'password17', 'User Seventeen', 0),
(18, 'user18', 'user18@example.com', 'password18', 'User Eighteen', 0),
(19, 'user19', 'user19@example.com', 'password19', 'User Nineteen', 0),
(20, 'user20', 'user20@example.com', 'password20', 'User Twenty', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answer`
--
ALTER TABLE `answer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `question_id` (`question_id`);

--
-- Indexes for table `exam`
--
ALTER TABLE `exam`
  ADD PRIMARY KEY (`test_id`,`ex_order`);

--
-- Indexes for table `exam_detail`
--
ALTER TABLE `exam_detail`
  ADD PRIMARY KEY (`test_id`,`question_id`),
  ADD KEY `question_id` (`question_id`);

--
-- Indexes for table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `topic_id` (`topic_id`);

--
-- Indexes for table `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`id`,`user_id`,`test_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `test_id` (`test_id`);

--
-- Indexes for table `result_detail`
--
ALTER TABLE `result_detail`
  ADD PRIMARY KEY (`id`,`answer_id`),
  ADD KEY `answer_id` (`answer_id`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `test_structure`
--
ALTER TABLE `test_structure`
  ADD PRIMARY KEY (`test_id`,`topic_id`),
  ADD KEY `topic_id` (`topic_id`);

--
-- Indexes for table `topic`
--
ALTER TABLE `topic`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `topic`
--
ALTER TABLE `topic`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `answer`
--
ALTER TABLE `answer`
  ADD CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);

--
-- Constraints for table `exam`
--
ALTER TABLE `exam`
  ADD CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`);

--
-- Constraints for table `exam_detail`
--
ALTER TABLE `exam_detail`
  ADD CONSTRAINT `exam_detail_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
  ADD CONSTRAINT `exam_detail_ibfk_2` FOREIGN KEY (`test_id`) REFERENCES `exam` (`test_id`);

--
-- Constraints for table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `question_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`);

--
-- Constraints for table `result`
--
ALTER TABLE `result`
  ADD CONSTRAINT `result_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `result_ibfk_2` FOREIGN KEY (`test_id`) REFERENCES `exam` (`test_id`);

--
-- Constraints for table `result_detail`
--
ALTER TABLE `result_detail`
  ADD CONSTRAINT `result_detail_ibfk_1` FOREIGN KEY (`id`) REFERENCES `result` (`id`),
  ADD CONSTRAINT `result_detail_ibfk_2` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`);

--
-- Constraints for table `test_structure`
--
ALTER TABLE `test_structure`
  ADD CONSTRAINT `test_structure_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`),
  ADD CONSTRAINT `test_structure_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
