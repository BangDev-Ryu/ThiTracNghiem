package com.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static List<List<String>> readCsvToList(String filePath, boolean hasHeader) {
        List<List<String>> lines = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allLines = csvReader.readAll();
            if (hasHeader && !allLines.isEmpty()) {
                allLines.remove(0);
            }
            lines = allLines.stream().map(Arrays::asList).collect(Collectors.toList());
        } catch (CsvException | IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static List<Question> readCsvToQuestions(String filePath, boolean hasHeader) {
        List<List<String>> csvData = readCsvToList(filePath, hasHeader);
        List<Question> questions = new ArrayList<>();
        for (List<String> row : csvData) {
            String questionText = row.get(0);
            String questionSearchText = row.get(1);
            String questionImage = row.get(2);
            List<Answer> answers = new ArrayList<>();
            for (int i = 3; i < row.size(); i += 4) {
                if (i >= row.size() || row.get(i).isEmpty()) {
                    break;
                }
                String answerText = row.get(i);
                String answerSearchText = row.get(i + 1);
                String answerImage = row.get(i + 2);
                boolean isRight = Boolean.parseBoolean(row.get(i + 3));
                answers.add(new Answer(answerText, answerSearchText, answerImage, isRight, true));
            }
            questions.add(new Question(questionText, questionSearchText, questionImage, answers, true));
        }
        return questions;
    }
}
