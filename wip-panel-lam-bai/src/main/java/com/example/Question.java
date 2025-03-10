package com.example;

import java.util.List;

public class Question {

    private String text;
    private String searchText;
    private String image;
    private List<Answer> answers;
    private boolean status;

    public Question() {}

    public Question(String text, String searchText, String image, List<Answer> answers, boolean status) {
        this.text = text;
        this.searchText = searchText;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
}
