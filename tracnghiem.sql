CREATE TABLE `test` (
  `id` int PRIMARY KEY,
  `test_code` varchar(255),
  `name` varchar(255),
  `time` int,
  `limit` tinyint,
  `date` datetime,
  `status` tinyint
);

CREATE TABLE `test_structure` (
  `test_id` int,
  `topic_id` int,
  `num_easy` int,
  `num_medium` int,
  `num_hard` int,
  PRIMARY KEY (`test_id`, `topic_id`)
);

CREATE TABLE `question` (
  `id` int PRIMARY KEY,
  `topic_id` int,
  `content` varchar(255),
  `picture` varchar(255),
  `level` varchar(255),
  `status` tinyint
);

CREATE TABLE `answer` (
  `id` int PRIMARY KEY,
  `question_id` int,
  `content` varchar(255),
  `picture` varchar(255),
  `is_right` tinyint,
  `status` tinyint
);

CREATE TABLE `topic` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `parent` int,
  `status` tinyint
);

CREATE TABLE `exam` (
  `test_id` int,
  `test_code` varchar(255),
  `ex_order` varchar(255),
  `ex_code` varchar(255),
  PRIMARY KEY (`test_id`, `ex_order`)
);

CREATE TABLE `exam_detail` (
  `test_id` int,
  `question_id` int,
  PRIMARY KEY (`test_id`, `question_id`)
);

CREATE TABLE `result` (
  `id` int,
  `user_id` int,
  `test_id` int,
  `mark` decimal,
  `date` datetime,
  PRIMARY KEY (`id`, `user_id`, `test_id`)
);

CREATE TABLE `result_detail` (
  `id` int,
  `answer_id` int,
  PRIMARY KEY (`id`, `answer_id`)
);

CREATE TABLE `user` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `email` varchar(255),
  `password` varchar(255),
  `fullname` varchar(255),
  `id_admin` tinyint
);

ALTER TABLE `test_structure` ADD FOREIGN KEY (`test_id`) REFERENCES `test` (`id`);

ALTER TABLE `test_structure` ADD FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`);

ALTER TABLE `question` ADD FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`);

ALTER TABLE `answer` ADD FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);

ALTER TABLE `exam_detail` ADD FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);

ALTER TABLE `result` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `result_detail` ADD FOREIGN KEY (`id`) REFERENCES `result` (`id`);

ALTER TABLE `result_detail` ADD FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`);

ALTER TABLE `exam` ADD FOREIGN KEY (`test_id`) REFERENCES `test` (`id`);

ALTER TABLE `exam_detail` ADD FOREIGN KEY (`test_id`) REFERENCES `exam` (`test_id`);

ALTER TABLE `result` ADD FOREIGN KEY (`test_id`) REFERENCES `exam` (`test_id`);
