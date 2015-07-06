package com.noisyninja.abheda_droid.pojo;

import com.noisyninja.abheda_droid.util.Constants;

/**
 * Created by ir2pi on 11/30/2014.
 */
public class MCQQuiz extends BaseLesson{

    public MCQQuiz()
    {
        module_type = Constants.MODULE_TYPE.MCQ_QUIZ;
    }

    String name;
    String description;
    String instruction;
    int daysToComplete;
    String mcqQuestions;
    //ArrayList<MCQQuestion> mcqQuestions;

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

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public int getDaysToComplete() {
        return daysToComplete;
    }

    public void setDaysToComplete(int daysToComplete) {
        this.daysToComplete = daysToComplete;
    }

    public String getMcqQuestions() {
        return mcqQuestions;
    }

    public void setMcqQuestions(String mcqQuestions) {
        this.mcqQuestions = mcqQuestions;
    }
}
