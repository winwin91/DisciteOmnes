package com.example.disciteomnes.model;

import java.util.List;

public class Group {
    public String id;
    public String name;
    public String description;
    public List<String> members;


    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}