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
  `test_code` varchar(255),
  `topic_id` int,
  `num_easy` int,
  `num_medium` int,
  `num_hard` int,
  PRIMARY KEY (`test_code`, `topic_id`)
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
  `test_code` varchar(255),
  `ex_order` varchar(255),
  `ex_code` varchar(255),
  PRIMARY KEY (`test_code`, `ex_order`)
);

CREATE TABLE `exam_detail` (
  `ex_code` varchar(255),
  `question_id` int
);

CREATE TABLE `result` (
  `id` int PRIMARY KEY,
  `user_id` int,
  `ex_code` varchar(255),
  `mark` decimal,
  `date` datetime
);

CREATE TABLE `result_detail` (
  `id` int PRIMARY KEY,
  `answer_id` int
);

CREATE TABLE `user` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `email` varchar(255),
  `password` varchar(255),
  `fullname` varchar(255),
  `id_admin` tinyint
);

ALTER TABLE `test_structure` ADD FOREIGN KEY (`test_code`) REFERENCES `test` (`test_code`);

ALTER TABLE `topic` ADD FOREIGN KEY (`id`) REFERENCES `test_structure` (`topic_id`);

ALTER TABLE `question` ADD FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`);

ALTER TABLE `answer` ADD FOREIGN KEY (`id`) REFERENCES `question` (`id`);

ALTER TABLE `exam` ADD FOREIGN KEY (`test_code`) REFERENCES `test` (`test_code`);

ALTER TABLE `exam_detail` ADD FOREIGN KEY (`ex_code`) REFERENCES `exam` (`ex_code`);

CREATE TABLE `exam_detail_question` (
  `exam_detail_question_id` int,
  `question_id` int,
  PRIMARY KEY (`exam_detail_question_id`, `question_id`)
);

ALTER TABLE `exam_detail_question` ADD FOREIGN KEY (`exam_detail_question_id`) REFERENCES `exam_detail` (`question_id`);

ALTER TABLE `exam_detail_question` ADD FOREIGN KEY (`question_id`) REFERENCES `question` (`id`);


ALTER TABLE `result` ADD FOREIGN KEY (`id`) REFERENCES `exam` (`ex_code`);

ALTER TABLE `result` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `result_detail` ADD FOREIGN KEY (`id`) REFERENCES `result` (`id`);

CREATE TABLE `result_detail_answer` (
  `result_detail_answer_id` int,
  `answer_id` int,
  PRIMARY KEY (`result_detail_answer_id`, `answer_id`)
);

ALTER TABLE `result_detail_answer` ADD FOREIGN KEY (`result_detail_answer_id`) REFERENCES `result_detail` (`answer_id`);

ALTER TABLE `result_detail_answer` ADD FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`);

