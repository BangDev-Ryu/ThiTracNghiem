package com.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

    public static String generateQuestionHtml(Question question) {
        Context context = new Context();
        context.setVariable("question", question);
        try {
            return questionViewTemplateEngineInitializer.get().process("question-view", context);
        } catch (ConcurrentException e) {
            e.printStackTrace();
            return "";
        }
    }
}
