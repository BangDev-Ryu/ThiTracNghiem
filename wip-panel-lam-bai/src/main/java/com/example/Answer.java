package com.example;

public class Answer {

    private String text;
    private String searchText;
    private String image;
    private boolean isRight;
    private boolean status;

    public Answer() {}

    public Answer(String text, String searchText, String image, boolean isRight, boolean status) {
        this.text = text;
        this.searchText = searchText;
        this.image = image;
        this.isRight = isRight;
        this.status = status;
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

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean isRight) {
        this.isRight = isRight;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
