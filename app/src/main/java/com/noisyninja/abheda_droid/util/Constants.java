package com.noisyninja.abheda_droid.util;

/**
 * Created by ir2pi on 11/30/2014.
 *
 * apache 2.0 libraries used:
 * AndroidTreeView https://github.com/bmelnychuk/AndroidTreeView
 * android-circlebutton https://github.com/markushi/android-circlebutton
 * android-HoloCircularProgressBar https://github.com/passsy/android-HoloCircularProgressBar
 * progressbutton https://github.com/f2prateek/progressbutton
 * CircleProgress https://github.com/lzyzsd/CircleProgress
 * https://github.com/idunnololz/AnimatedExpandableListView
 * Picassohttps://github.com/square/picasso/
 * glide https://github.com/bumptech/glide
 * MIT libraries
 * AndroidImageSlider
 * https://github.com/daimajia/AndroidImageSlider
 * SeekArc https://github.com/TriggerTrap/SeekArc
 *         https://github.com/SemonCat/SeekArc
 * swipelistview
 *         https://github.com/47deg/android-swipelistview#license
 * DraggableGridView
 *         https://github.com/thquinn/DraggableGridView
 * NumberProgressBar
 *         https://github.com/daimajia/NumberProgressBar
 * LoadIndicators
 *         https://github.com/adrian110288/LoadIndicators
 * AndroidViewAnimations
 *         https://github.com/daimajia/AndroidViewAnimations
 *
 *         Free for personal and commercial use with attribution to 1001FreeDownloads.com
 *
 *
 */
public class Constants {


    public static String EMAIL = "abhedafoundation@gmail.com";

    public static int TOPIC_ID = 0;//english
    public static int COURSE_ID = 0;//BCP
    public static int MODULE_ID = 0;//BCP 1
    public static int LESSON_QUIZ_ID = 0; //Lesson 1


    public static String HTML_BREAK = "<BR>";
    public static String HTML_BOLD_PRE = "<B>";
    public static String HTML_BOLD_POST = "</B>";
    public static String HTML_ITALICS_PRE = "<I>";
    public static String HTML_ITALICS_POST = "</I>";

    public static String BLANK = "";
    public static String QUESTION = "Q:";
    public static String CORRECT = "✓";// ✓,✔, ☑,
    public static String WRONG = "✘";//✖,x, ×, X, ✕, ☓, ✖, ✗, ✘,☒
    public static String SPLIT_DELIMITER = "@";
    public static String NEWLINE = "\n";
    public static String SPACE = " ";
    public static String UTF8 = "UTF8";
    public static String BACKSLASH = "/";
    public static String DRAWABLE = "drawable";
    public static String DATA_JSON = "data.json";
    public static String DATA_ZIP = "abheda.zip";
    public static String DATA_FOLDER = "abheda/";
    public static String SD_CARD = "/sdcard/";
    public static String IMAGE_EXTENSION_JPG = ".jpg";
    public static String IMAGE_EXTENSION_PNG = ".png";
    public static String URL_STORE = "https://dl.dropbox.com/s/o2qh922feobk7sg/data.zip?dl=0";//"https://drive.google.com/uc?export=download&id=0B3-WqXENqs7dWEJtTURZNWtDTmc";
    public static String URL_STORE_KEY = "URL_STORE_KEY";
    public static String FIRST_RUN_KEY = "FIRST_RUN_KEY";

    /*public static String JSON = "data.json";
    public static String _DATA_FOLDER = "/abheda/";
    public static String _DATA_FILE = _DATA_FOLDER+JSON;
    public static String _SDCARD = "/sdcard/";
    public static String _LOCAL_STORE = _SDCARD+"abheda.zip";*/

    public enum Sound {
        CLICK,
        RIGHT,
        WRONG
    }
    public enum MODULE_TYPE {
        LESSON,
        FLASHCARD,
        MCQ_QUIZ,
        MCQ_IMAGE_QUIZ,
        PICTURE_MATCH_QUIZ,
        ORDER_GAME_QUIZ,
        SIMPLE_QUIZ
    }
    public enum PROGRESS_STYLE {
        DETERMINATE,
        INDETERMINATE
    }


    public static String FRAGMENT_DATA = "FRAGMENT_DATA";
    public static String FRAGMENT_TYPE = "FRAGMENT_TYPE";

    public static int TAB_COUNT = 3;
    public static int PROGRESS = 31;
    public static long SLEEP_TIME_2000 = 2000;    // Sleep for some time
    public static long SLEEP_TIME_1000 = 1000;    // Sleep for some time
    public static long SLEEP_TIME_20 = 20;    // Sleep for some time
    public static long ANIMATION_TIME_700 = 700;    // Sleep for some time
    public static String QUIZ_COMPLETED_TEXT = "Quiz Completed!";
    public static String PROGRESS_TEXT = "Working...";
    public static String PROGRESS_PERCENT = "%";
    public static String DOWNLOAD_TEXT = "Downloading file..";


    //error
    public static String ERROR = "ERROR:";
    public static String ERROR_INCOMPLETE = "please complete the question";
    public static String ERROR_NO_SELECTION = "Please select an answer.";
    public static String ERROR_NO_NETWORK = "NO_NETWORK";
    public static String ERROR_INVALID_JSON = "INVALID_JSON_OR_NOT_FOUND";
    public static String ERROR_FILE_NOT_FOUND = "FILE_NOT_FOUND";
    //info
    public static String INFO = "INFO:";
    public static String INFO_SUCCESS_DOWNLOAD = "Download success";
    public static String INFO_NO_LESSON = "ERROR_NO_LESSON";

    public static String CRITTERCISM_APP_ID = "5500497db59ef2d535335cf3";

    public static String HTTP_FLAG = "http";

}
