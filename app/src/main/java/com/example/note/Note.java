package com.example.note;

public class Note {
    private int id;
    private String title;
    private String description;
    private  String dayOfWeek;
    private int priority;

    public Note(String title, String description, String dayOfWeek, int priority, int id) {
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getPriority() {
        return priority;
    }
}
