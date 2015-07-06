package com.noisyninja.abheda_droid.pojo;

import com.noisyninja.abheda_droid.util.Constants;

/**
 * Created by ir2pi on 11/30/2014.
 */
public class Lesson extends BaseLesson{
    String name;
    String description;
    String instruction;
    String image;
    String pages;
    boolean isFlashCard;

    public Lesson()
    {
        module_type = Constants.MODULE_TYPE.LESSON;
    }

    public boolean isFlashCard() {
        return isFlashCard;
    }

    public void setFlashCard(boolean isFlashCard) {
        this.isFlashCard = isFlashCard;
        module_type = isFlashCard == true? Constants.MODULE_TYPE.FLASHCARD:Constants.MODULE_TYPE.LESSON;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
}
