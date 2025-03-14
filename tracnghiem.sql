-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 14, 2025 lúc 04:45 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `tracnghiem`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `answer`
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
-- Cấu trúc bảng cho bảng `exam`
--

CREATE TABLE `exam` (
  `test_id` int(11) NOT NULL,
  `test_code` varchar(255) DEFAULT NULL,
  `ex_order` varchar(255) NOT NULL,
  `ex_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `exam_detail`
--

CREATE TABLE `exam_detail` (
  `test_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `question`
--

CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `topic_id` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `result`
--

CREATE TABLE `result` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `mark` decimal(10,0) DEFAULT NULL,
  `date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `result_detail`
--

CREATE TABLE `result_detail` (
  `id` int(11) NOT NULL,
  `answer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test`
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

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test_structure`
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
-- Cấu trúc bảng cho bảng `topic`
--

CREATE TABLE `topic` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
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
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `answer`
--
ALTER TABLE `answer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `question_id` (`question_id`);

--
-- Chỉ mục cho bảng `exam`
--
ALTER TABLE `exam`
  ADD PRIMARY KEY (`test_id`,`ex_order`);

--
-- Chỉ mục cho bảng `exam_detail`
--
ALTER TABLE `exam_detail`
  ADD PRIMARY KEY (`test_id`,`question_id`),
  ADD KEY `question_id` (`question_id`);

--
-- Chỉ mục cho bảng `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `topic_id` (`topic_id`);

--
-- Chỉ mục cho bảng `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`id`,`user_id`,`test_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `test_id` (`test_id`);

--
-- Chỉ mục cho bảng `result_detail`
--
ALTER TABLE `result_detail`
  ADD PRIMARY KEY (`id`,`answer_id`),
  ADD KEY `answer_id` (`answer_id`);

--
-- Chỉ mục cho bảng `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `test_structure`
--
ALTER TABLE `test_structure`
  ADD PRIMARY KEY (`test_id`,`topic_id`),
  ADD KEY `topic_id` (`topic_id`);

--
-- Chỉ mục cho bảng `topic`
--
ALTER TABLE `topic`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `answer`
--
ALTER TABLE `answer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `question`
--
ALTER TABLE `question`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `result`
--
ALTER TABLE `result`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `test`
--
ALTER TABLE `test`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `topic`
--
ALTER TABLE `topic`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `answer`
--
ALTER TABLE `answer`
  ADD CONSTRAINT `answer_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);

--
-- Các ràng buộc cho bảng `exam`
--
ALTER TABLE `exam`
  ADD CONSTRAINT `exam_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`);

--
-- Các ràng buộc cho bảng `exam_detail`
--
ALTER TABLE `exam_detail`
  ADD CONSTRAINT `exam_detail_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`),
  ADD CONSTRAINT `exam_detail_ibfk_2` FOREIGN KEY (`test_id`) REFERENCES `exam` (`test_id`);

--
-- Các ràng buộc cho bảng `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `question_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`);

--
-- Các ràng buộc cho bảng `result`
--
ALTER TABLE `result`
  ADD CONSTRAINT `result_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `result_ibfk_2` FOREIGN KEY (`test_id`) REFERENCES `exam` (`test_id`);

--
-- Các ràng buộc cho bảng `result_detail`
--
ALTER TABLE `result_detail`
  ADD CONSTRAINT `result_detail_ibfk_1` FOREIGN KEY (`id`) REFERENCES `result` (`id`),
  ADD CONSTRAINT `result_detail_ibfk_2` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`);

--
-- Các ràng buộc cho bảng `test_structure`
--
ALTER TABLE `test_structure`
  ADD CONSTRAINT `test_structure_ibfk_1` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`),
  ADD CONSTRAINT `test_structure_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
