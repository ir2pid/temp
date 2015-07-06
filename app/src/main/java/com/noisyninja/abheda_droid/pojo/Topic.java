package com.noisyninja.abheda_droid.pojo;

import java.util.ArrayList;

/**
 * Created by ir2pi on 12/13/2014.
 */
public class Topic extends BasePojo {

    String name;
    String description;
    int level;
    int completion;
    int marks;

    public ArrayList<Courses> getCoursesList() {
        return coursesList;
    }

    public void setCoursesList(ArrayList<Courses> coursesList) {
        this.coursesList = coursesList;
    }

    ArrayList<Courses> coursesList;

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCompletion() {
        return completion;
    }

    public void setCompletion(int completion) {
        this.completion = completion;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
