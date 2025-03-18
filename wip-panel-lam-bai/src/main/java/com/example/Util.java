package com.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.concurrent.LazyInitializer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class Util {

    public static List<List<String>> readCsvToList(String filePath, boolean hasHeader) {
        List<List<String>> lines = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allLines = csvReader.readAll();
            if (hasHeader && !allLines.isEmpty()) {
                allLines.remove(0);
            }
            lines = allLines.stream().map(Arrays::asList).toList();
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
            int difficultyLevel = Integer.parseInt(row.get(3));
            DifficultyEnum difficulty = DifficultyEnum.valueOfLevel(difficultyLevel);
            List<Answer> answers = new ArrayList<>();
            for (int i = 4, order = 0; i < row.size(); i += 4, order++) {
                if (i >= row.size() || row.get(i).isEmpty()) {
                    break;
                }
                String answerText = row.get(i);
                String answerSearchText = row.get(i + 1);
                String answerImage = row.get(i + 2);
                boolean isRight = Boolean.parseBoolean(row.get(i + 3));
                answers.add(new Answer(answerText, answerSearchText, answerImage, isRight, order, true));
            }
            questions.add(new Question(questionText, questionSearchText, questionImage, answers, true, difficulty));
        }
        return questions;
    }

    private static final LazyInitializer<TemplateEngine> questionViewTemplateEngineInitializer = new LazyInitializer<
        TemplateEngine
    >() {
        @Override
        protected TemplateEngine initialize() {
            final TemplateEngine templateEngine = new TemplateEngine();
            final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setPrefix("/templates/");
            resolver.setSuffix(".html");
            resolver.setCharacterEncoding("UTF-8");
            resolver.setTemplateMode(TemplateMode.HTML);
            templateEngine.setTemplateResolver(resolver);
            return templateEngine;
        }
    };

    public static String generateQuestionHtml(Question question, int order) {
        Context context = new Context();
        context.setVariable("question", question);
        context.setVariable("order", order);
        try {
            return questionViewTemplateEngineInitializer.get().process("question-view", context);
        } catch (ConcurrentException e) {
            e.printStackTrace();
            return "";
        }
    }

    // https://learn.microsoft.com/en-us/office/troubleshoot/excel/convert-excel-column-numbers
    public static String getExcelColumnName(int index) {
        if (index < 0) {
            return "#VALUE!";
        }
        StringBuilder result = new StringBuilder();
        index++;
        while (index > 0) {
            int remainder = (index - 1) % 26;
            result.insert(0, (char) (remainder + 'A'));
            index = (index - 1) / 26;
        }
        return result.toString();
    }

    public static <T> List<T> cyclicRandomSampling(List<T> source, int count) {
        return Stream.generate(() -> {
            List<T> shuffled = new ArrayList<>(source);
            Collections.shuffle(shuffled);
            return shuffled;
        })
            .flatMap(List::stream)
            .limit(count)
            .toList();
    }
}
