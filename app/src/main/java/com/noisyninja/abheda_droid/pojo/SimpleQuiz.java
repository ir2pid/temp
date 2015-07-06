package com.noisyninja.abheda_droid.pojo;

import com.noisyninja.abheda_droid.util.Constants;

/**
 * Created by sudipta.a.dutta on 28/06/15.
 */
public class SimpleQuiz extends BaseLesson{

    public SimpleQuiz()
    {
        module_type = Constants.MODULE_TYPE.SIMPLE_QUIZ;
    }

    String name;
    String description;
    String simpleQuestions;

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

    public String getSimpleQuestions() {
        return simpleQuestions;
    }

    public void setSimpleQuestions(String simpleQuestions) {
        this.simpleQuestions = simpleQuestions;
    }
}
