package com.foxminded.universitydatabase.db_layer.entities;

import java.util.ArrayList;

public class Faculty {
    private int id;
    private String name;
    private String description;
    private ArrayList<Student> students = new ArrayList<Student>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
