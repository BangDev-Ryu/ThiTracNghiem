package com.example;

public class Topic {

    private Topic parent;
    private String name;

    public Topic(Topic parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public Topic getParent() {
        return parent;
    }

    public void setParent(Topic parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
