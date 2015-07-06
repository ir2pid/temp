package com.noisyninja.abheda_droid.control;

/**
 * Created by ir2pi on 11/30/2014.
 */
public class ListLessonDetailItem {

    public final String image;       // the drawable for the ListView item ImageView
    public final String name;        // the text for the ListView item name
    public final String text;  // the text for the ListView item text
    public final String ltext;  // the text for the ListView item text
    public final String rtext;  // the text for the ListView item text
    public final String utext;  // the text for the ListView item text
    public final String dtext;  // the text for the ListView item text
    public final String description;  // the text for the ListView item description

    public ListLessonDetailItem(String image, String name, String text, String ltext, String rtext, String utext, String dtext, String description) {
        this.image = image;
        this.name = name;
        this.text = text;
        this.ltext = ltext;
        this.rtext = rtext;
        this.utext = utext;
        this.dtext = dtext;
        this.description = description;
    }
}