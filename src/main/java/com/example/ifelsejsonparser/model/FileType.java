package com.example.ifelsejsonparser.model;

public enum FileType {
    JSON("json");

    FileType(String name) {
        this.name = name;
    }

    String name;

    public String getName() {
        return name;
    }
}
