package com.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Exam {

    private List<Question> questions;
    private int order;

    public Exam(List<Question> questions, int order) {
        this.questions = questions;
        this.order = order;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public static Exam generateExam(
        QuestionListByTopic questionListByTopic,
        HashMap<DifficultyEnum, Integer> questionCountPerLevel
    ) {
        List<Question> selectedQuestions = questionCountPerLevel
            .entrySet()
            .stream()
            .flatMap(entry -> {
                DifficultyEnum difficulty = entry.getKey();
                int requiredCount = entry.getValue();

                List<Question> filteredQuestions = questionListByTopic
                    .getQuestions()
                    .stream()
                    .filter(q -> q.getDifficulty() == difficulty)
                    .toList();

                return Util.cyclicRandomSampling(filteredQuestions, requiredCount).stream();
            })
            .collect(Collectors.toList());
        Collections.shuffle(selectedQuestions);
        return new Exam(selectedQuestions, 0);
    }

    public static List<Exam> generateExams(
        QuestionListByTopic questionListByTopic,
        HashMap<DifficultyEnum, Integer> questionCountPerLevel,
        int orderStart,
        int count
    ) {
        return IntStream.range(0, count)
            .mapToObj(n -> {
                Exam exam = generateExam(questionListByTopic, questionCountPerLevel);
                exam.setOrder(orderStart + n);
                return exam;
            })
            .toList();
    }

    // tao 10 exam dau tien theo yeu cau de bai
    public static List<Exam> generateDefaultExams(
        QuestionListByTopic questionListByTopic,
        HashMap<DifficultyEnum, Integer> questionCountPerLevel
    ) {
        return generateExams(questionListByTopic, questionCountPerLevel, 0, 10);
    }
}
