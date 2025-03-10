package com.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class Question {

    private String text;
    private String searchText;
    private Path image;
    private String imageUri;
    private List<Answer> answers;
    private boolean status;

    public Question() {}

    public Question(String text, String searchText, Path image, List<Answer> answers, boolean status) {
        this.text = text;
        this.searchText = searchText;
        this.image = image;
        this.imageUri = image != null ? image.toUri().toString() : null;
        this.answers = answers;
        this.status = status;
    }

    public Question(String text, String searchText, String imagePath, List<Answer> answers, boolean status) {
        this.text = text;
        this.searchText = searchText;
        this.image = StringUtils.isBlank(imagePath) ? null : Paths.get(imagePath);
        this.imageUri = this.image != null ? this.image.toUri().toString() : null;
        this.answers = answers;
        this.status = status;
    }

    public boolean isMultipleChoices() {
        return answers.stream().filter(Answer::isRight).limit(2).count() > 1;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Path getImage() {
        return image;
    }

    public void setImage(Path image) {
        this.image = image;
        this.imageUri = image != null ? image.toUri().toString() : null;
    }

    public void setImage(String imagePath) {
        this.image = StringUtils.isBlank(imagePath) ? null : Paths.get(imagePath);
        this.imageUri = this.image != null ? this.image.toUri().toString() : null;
    }

    public String getImageUri() {
        return imageUri;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
}
