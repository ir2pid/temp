package com.noisyninja.abheda_droid.activity;

import android.app.Activity;
import android.os.Bundle;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.pojo.Module;
import com.noisyninja.abheda_droid.pojo.Courses;
import com.noisyninja.abheda_droid.pojo.Lesson;
import com.noisyninja.abheda_droid.pojo.Lessons;
import com.noisyninja.abheda_droid.pojo.MCQQuiz;
import com.noisyninja.abheda_droid.pojo.Quizzes;

import java.util.ArrayList;

/**
 * Created by ir2pi on 12/3/2014.
 */
public class About extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        Courses courses = new Courses();
        ArrayList<Module> moduleList = new ArrayList<Module>();

        Module module = new Module();
    }

    public Lessons getLessons()
    {
        Lessons lessons = new Lessons();

        ArrayList<Lesson> lessonsList = new ArrayList<Lesson>();

        lessons.setLessons(lessonsList);
        return lessons;
    }

    public Quizzes getQuizzes()
    {
        Quizzes quizzes = new Quizzes();
        ArrayList<MCQQuiz> quizzesList = new ArrayList<MCQQuiz>();

        return quizzes;
    }
}
