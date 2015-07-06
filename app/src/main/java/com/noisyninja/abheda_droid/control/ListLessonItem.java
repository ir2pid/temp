package com.noisyninja.abheda_droid.control;

import com.noisyninja.abheda_droid.util.Constants.MODULE_TYPE;

/**
 * Created by ir2pi on 12/7/2014.
 */
public class ListLessonItem {
   // public final Drawable icon;       // the drawable for the ListView item ImageView
    public final String title;        // the text for the ListView item title
    public final String description;  // the text for the ListView item description
    public final MODULE_TYPE module_type;

    public ListLessonItem(String title, String description, MODULE_TYPE module_type) {
        //this.icon = icon;
        this.title = title;
        this.description = description;
        this.module_type = module_type;

    }
    /*public ListLessonItem(Drawable icon, String title, String description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }*/
}
