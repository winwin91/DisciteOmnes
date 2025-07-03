package com.example.disciteomnes.model;

public class Task {
    public String id;
    public String title;
    public String description;
    public String dueDate;
    public boolean completed;
    public String assignedTo;


    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getDueDate() {
        return dueDate;
    }
    public String getAssignedTo() {
        return assignedTo;
    }
    public boolean isCompleted() {
        return completed;
    }

    // ✅ SETTER
    public void setId(String id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
