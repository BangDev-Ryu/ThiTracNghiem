package com.example;

import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

    public MainFrame() {
        super();
        setTitle("Main Frame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        WorkPanel workFrame = new WorkPanel();

        // TODO: doc list question by topic from db
        List<Question> questionList = Util.readCsvToQuestions("./questions.csv", true);
        QuestionListByTopic questionListByTopic = new QuestionListByTopic(questionList, new Topic(null, "test"));
        HashMap<DifficultyEnum, Integer> questionCountPerLevel = new HashMap<>();
        questionCountPerLevel.put(DifficultyEnum.EASY, 3);
        questionCountPerLevel.put(DifficultyEnum.NORMAL, 3);
        questionCountPerLevel.put(DifficultyEnum.DIFFICULT, 3);
        // NOTE: dat breakpoint o day de test
        List<Exam> exams = Exam.generateDefaultExams(questionListByTopic, questionCountPerLevel);
        Exam exam = exams.get(0); // Use the first generated exam
        workFrame.setExam(exam);

        add(workFrame);
    }
}
