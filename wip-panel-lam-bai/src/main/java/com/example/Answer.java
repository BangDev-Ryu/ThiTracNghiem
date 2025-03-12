package com.example;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

public class Answer {

    private String text;
    private String searchText;
    private Path image;
    private boolean isRight;
    private int order;
    private boolean status;
    private String imageUri;

    public Answer() {}

    public Answer(String text, String searchText, String image, boolean isRight, int order, boolean status) {
        this.text = text;
        this.searchText = searchText;
        setImage(image);
        this.isRight = isRight;
        this.order = order;
        this.status = status;
    }

    public Answer(String text, String searchText, Path image, boolean isRight, int order, boolean status) {
        this.text = text;
        this.searchText = searchText;
        setImage(image);
        this.isRight = isRight;
        this.order = order;
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

    public Path getImage() {
        return image;
    }

    public void setImage(String image) {
        if (StringUtils.isBlank(image)) {
            this.image = null;
            this.imageUri = null;
        } else {
            this.image = Paths.get(image);
            this.imageUri = this.image.toUri().toString();
        }
    }

    public void setImage(Path image) {
        this.image = image;
        if (image == null) {
            this.imageUri = null;
        } else {
            this.imageUri = image.toUri().toString();
        }
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getImageUri() {
        return imageUri;
    }

    // lay ra chu cai ABC theo order (0:A, 1:B, ...)
    public String getExcelColumnName() {
        return Util.getExcelColumnName(order);
    }
}
