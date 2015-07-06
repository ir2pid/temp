package com.noisyninja.abheda_droid.pojo;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ir2pi on 2/19/2015.
 */
public class OrderGameQuestion extends BasePojo {

    Map<Integer,String> words;
    String question;
    ArrayList<ArrayList<Integer>>answers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<Integer, String> getWords() {
        return words;
    }

    public void setWords(Map<Integer, String> words) {
        this.words = words;
    }

    public ArrayList<ArrayList<Integer>> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<ArrayList<Integer>> answers) {
        this.answers = answers;
    }
}