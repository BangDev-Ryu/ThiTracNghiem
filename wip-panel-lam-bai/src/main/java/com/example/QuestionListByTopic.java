package com.example;

import java.util.List;

public class QuestionListByTopic {

    private List<Question> questions;
    private Topic topic;

    public QuestionListByTopic(List<Question> questions, Topic topic) {
        this.questions = questions;
        this.topic = topic;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
