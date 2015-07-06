package com.noisyninja.abheda_droid.pojo;

import com.noisyninja.abheda_droid.util.Constants;

/**
 * Created by ir2pi on 12/24/2014.
 */
public class PictureMatchQuiz extends BaseLesson {

    public PictureMatchQuiz()
    {
        module_type = Constants.MODULE_TYPE.PICTURE_MATCH_QUIZ;
    }

    String name;
    String description;
    String pictureMatchQuestions;
    //ArrayList<PictureMatchQuestion> pictureMatchQuestions;

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

    public String getPictureMatchQuestions() {
        return pictureMatchQuestions;
    }

    public void setPictureMatchQuestions(String pictureMatchQuestions) {
        this.pictureMatchQuestions = pictureMatchQuestions;
    }
}
