package com.noisyninja.abheda_droid.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crittercism.app.Crittercism;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.control.CircleTransform;
import com.noisyninja.abheda_droid.control.FlipAnimation;
import com.noisyninja.abheda_droid.control.quickaction.ActionItem;
import com.noisyninja.abheda_droid.control.quickaction.QuickAction;
import com.noisyninja.abheda_droid.fragment.FlashcardDetailFrag;
import com.noisyninja.abheda_droid.fragment.LessonDetailFrag;
import com.noisyninja.abheda_droid.fragment.MCQDetailFrag;
import com.noisyninja.abheda_droid.fragment.OrderGameDetailFragNew;
import com.noisyninja.abheda_droid.fragment.PictureMatchDetailFrag;
import com.noisyninja.abheda_droid.fragment.SimpleQuizDetailFrag;
import com.noisyninja.abheda_droid.pojo.misc.IntegerStringPair;
import com.noisyninja.abheda_droid.util.Constants.MODULE_TYPE;
import com.noisyninja.abheda_droid.util.Constants.PROGRESS_STYLE;

import junit.framework.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import at.markushi.ui.CircleButton;

/**
 * Created by ir2pi on 12/2/2014.
 */
public class Utils {

    private static String TAG = Utils.class.getSimpleName();
    private static ProgressDialog mProgressDialog;

    public static int getDrawable(Context context, String name) {
        // use -> image.setImageResource(getDrawable(this,"image"));

        Assert.assertNotNull(context);
        Assert.assertNotNull(name);

        int id = context.getResources().getIdentifier(name,
                Constants.DRAWABLE, context.getPackageName());

        return id != 0 ? id : context.getResources().getIdentifier("imageholder",
                Constants.DRAWABLE, context.getPackageName());
    }

    public static String getTag(Class c) {
        return c.getSimpleName();
    }

    public static void startActivity(Context context, Class nextClass) {
        Intent intent = new Intent(context, nextClass);
        //myIntent.putExtra("key", value); //Optional parameters
        context.startActivity(intent);
    }

    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void makeAnimation(View view, Techniques techniques) {
        YoYo.with(techniques)
                .duration(Constants.ANIMATION_TIME_700)
                .playOn(view);
    }

    public static void makeAnimation(View view) {
        makeAnimation(view, Techniques.RubberBand);
    }

    public static void playSound(Context context, Constants.Sound sound) {
        MediaPlayer mp;
        switch (sound) {
            case RIGHT:
                mp = MediaPlayer.create(context, R.raw.tethys);
                break;
            case WRONG:
                mp = MediaPlayer.create(context, R.raw.iapetus);
                break;
            case CLICK:
                mp = MediaPlayer.create(context, R.raw.unlock);
                break;
            default:
                mp = MediaPlayer.create(context, R.raw.elara);
                break;
        }
        mp.start();
    }

    public static void setBgGradient() {
        //View layout = findViewById(R.id.mainlayout);

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{0xFF616261, 0xFF131313});
        gd.setCornerRadius(0f);

        //layout.setBackgroundDrawable(gd);
    }

    public static void showDialog(final Fragment fragment, String title, String message, boolean isInfo) {
        // custom dialog

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(fragment.getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (fragment instanceof IDialogCallback) {
                            ((IDialogCallback) fragment).ok(dialog);
                        } else {
                            dialog.dismiss();
                        }
                    }
                });

        if (!isInfo) {

            dialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (fragment instanceof IDialogCallback) {
                        ((IDialogCallback) fragment).cancel(dialog);
                    }
                }
            }).setIcon(android.R.drawable.ic_dialog_alert);
        }
        AlertDialog dialog = dialogBuilder.create();

        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation_down_up;
        dialog.show();
    }

    public static void showResult(Context context, boolean value) {
        showResult(context, value, null, null, null, null);
    }
    public static void showResult(Context context, boolean state,String sQuestion, String sCorrect, String sWrong){
        showResult(context, state, sQuestion, sCorrect, sWrong, null);
    }
    public static void showResult(Context context, boolean state,String sQuestion, String sCorrect, String sWrong, final Fragment fragment) {
        // custom dialog
        final Dialog dialog = new Dialog(context);//, R.style.TransparentDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_quiz_result);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(fragment != null){
                    if (fragment instanceof IDialogCallback) {
                        ((IDialogCallback) fragment).ok(dialog);
                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation_down_up;

        // set the custom dialog components - button
        CircleButton buttonModule1 = (CircleButton) dialog.findViewById(R.id.circleButton1);
        CircleButton buttonModule2 = (CircleButton) dialog.findViewById(R.id.circleButton2);
        TextView question = (TextView) dialog.findViewById(R.id.question);
        TextView correct = (TextView) dialog.findViewById(R.id.correctno);
        TextView wrong = (TextView) dialog.findViewById(R.id.wrong);

        if (state) {
            Utils.playSound(context, Constants.Sound.RIGHT);
            buttonModule1.setVisibility(View.VISIBLE);
            buttonModule2.setVisibility(View.INVISIBLE);
        } else {
            Utils.playSound(context, Constants.Sound.WRONG);
            buttonModule1.setVisibility(View.INVISIBLE);
            buttonModule2.setVisibility(View.VISIBLE);
        }

        if(sQuestion!=null)
        {
            Utils.setText(question, sQuestion);
            question.setVisibility(View.VISIBLE);
        }
        if(sCorrect!=null)
        {
            Utils.setText(correct, sCorrect);
            correct.setVisibility(View.VISIBLE);
        }
        if(sWrong!=null)
        {
            Utils.setText(wrong, sWrong);
            wrong.setVisibility(View.VISIBLE);
        }
        dialog.show();
    }
    public static void showInstriction(Context context) {
        // custom dialog
        final Dialog dialog = new Dialog(context);//, R.style.TransparentDialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_instruction_flashcard);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation_down_up;

        dialog.show();
    }

    public static void showQuickAction(Activity activity, View view) {

        ActionItem nextItem = new ActionItem(1, "Next", activity.getResources().getDrawable(R.drawable.bulb2));
        ActionItem prevItem = new ActionItem(2, "Prev", activity.getResources().getDrawable(R.drawable.check_book));
        ActionItem searchItem = new ActionItem(3, "Find", activity.getResources().getDrawable(R.drawable.bookmark_add));
        ActionItem infoItem = new ActionItem(4, "Info", activity.getResources().getDrawable(R.drawable.check_book));
        ActionItem eraseItem = new ActionItem(5, "Clear", activity.getResources().getDrawable(R.drawable.bookmark_remove));
        ActionItem okItem = new ActionItem(6, "OK", activity.getResources().getDrawable(R.drawable.book4));

        //use setSticky(true) to disable QuickAction dialog being dismissed after an item is clicked
        prevItem.setSticky(true);
        nextItem.setSticky(true);

        //create QuickAction. Use QuickAction.VERTICAL or QuickAction.HORIZONTAL param to define layout
        //orientation
        final QuickAction quickAction = new QuickAction(activity, QuickAction.VERTICAL);

        //add action items into QuickAction
        quickAction.addActionItem(nextItem);
        quickAction.addActionItem(prevItem);
        quickAction.addActionItem(searchItem);
        quickAction.addActionItem(infoItem);
        quickAction.addActionItem(eraseItem);
        quickAction.addActionItem(okItem);

        //Set listener for action item clicked
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
            @Override
            public void onItemClick(QuickAction source, int pos, int actionId) {
                ActionItem actionItem = quickAction.getActionItem(pos);

                //here we can filter which action item was clicked with pos or actionId parameter
                if (actionId == 1) {
                    //Toast.makeText(activity, "Let's do some search action", Toast.LENGTH_SHORT).show();
                } else if (actionId == 2) {
                    //Toast.makeText(getApplicationContext(), "I have no info this time", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), actionItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set listnener for on dismiss event, this listener will be called only if QuickAction dialog was dismissed
        //by clicking the area outside the dialog.
        quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {
            @Override
            public void onDismiss() {
                // Toast.makeText(getApplicationContext(), "Dismissed", Toast.LENGTH_SHORT).show();
            }
        });

        quickAction.show(view);
    }

    public static void handleError(Context context, Exception e) {
        crittercismException(e);
        Log.e(Utils.class.getCanonicalName(), Constants.ERROR + e.getMessage());
        Toast.makeText(context, Constants.ERROR + e.getMessage(), Toast.LENGTH_SHORT).show();

    }

    public static void handleError(Context context, String e) {

        Log.e(Utils.class.getCanonicalName(), Constants.ERROR + e);
        Toast.makeText(context, Constants.ERROR + e, Toast.LENGTH_SHORT).show();

    }

    public static void handleInfo(Context context, String e) {

        Log.i(Utils.class.getCanonicalName(), Constants.INFO + e);
        Toast.makeText(context, Constants.INFO + e, Toast.LENGTH_SHORT).show();

    }

    public static void write(Context context, String data) {
        write(context, Utils.getTempString(Constants.SD_CARD, Constants.DATA_FOLDER, Constants.DATA_JSON), data);
    }

    public static void write(Context context, String fileName, String data) {
        try {
            FileOutputStream fOut = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
            fOut.write(data.getBytes());
            fOut.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Utils.handleError(context, e);
            e.printStackTrace();
        }
    }

    public static Object getObject(Context context, String fileName, Class clazz) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        Gson gson = gsonBuilder.create();
        Object o = null;
        /*
        List<Post> posts = new ArrayList<Post>();
        posts = Arrays.asList(gson.fromJson(reader, Post[].class));
        */
        String json = Utils.read(context, Utils.getTempString(Constants.SD_CARD, Constants.DATA_FOLDER, fileName));
        if (json == null || json.length() < 2) {
            json = Utils.getStringFromAsset(context, fileName);//Utils.getTempString(Constants.DATA_FOLDER, fileName));
        }
        try {
          o = gson.fromJson(json, clazz);
        } catch (Exception e) {
            Utils.handleError(context, e);
        }
        finally
        {
            return o;
        }
    }

    public static String read(Context context) {
        return read(context, Utils.getTempString(Constants.SD_CARD, Constants.DATA_FOLDER, Constants.DATA_JSON));
    }

    public static String read(Context context, String fileName) {
        StringBuilder temp = new StringBuilder();
        try {
            File fileDir = new File(fileName);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), Constants.UTF8));

            String str;

            while ((str = in.readLine()) != null) {
                temp.append(str);
            }

            in.close();
        } catch (UnsupportedEncodingException e) {
            Utils.handleError(context, e);
        } catch (IOException e) {
            Utils.handleError(context, e);
        } catch (Exception e) {
            Utils.handleError(context, e);
        }

        return temp.toString();

    }

    public static Drawable getDrawableFromAsset(Context context, String fileName){
        Drawable d = null;
        try
        {
            // get input stream
            InputStream ims = context.getAssets().open(fileName);
            // load image as Drawable
            d = Drawable.createFromStream(ims, null);
            // set image to ImageView

        }
        catch(IOException ex)
        {
            Utils.handleError(context, ex);
        }
        return d != null ? d : context.getResources().getDrawable( R.drawable.imageholder );
    }
    public static String getStringFromAsset(Context context, String fileName) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(fileName);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ( (str = in.readLine()) != null ) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }

            return buf.toString();
        } catch (IOException e) {
            crittercismException(e);
            Log.e(TAG, "Error opening asset " + fileName);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    crittercismException(e);
                    Log.e(TAG, "Error closing asset " + fileName);
                }
            }
        }
        return null;
    }

    public static void styleCircle(CircleProgress cProgress, Context context)
    {
        cProgress.setUnfinishedColor(context.getResources().getColor(R.color.button_transparent_white));
        cProgress.setFinishedColor(context.getResources().getColor(R.color.button_off_white));
        cProgress.setTextColor(context.getResources().getColor(R.color.button_gray));
        //cProgress.setTextSize(8);
        /*arcProgress.setBottomText(context.getString(R.string.progress));
        arcProgress.setFinishedStrokeColor(context.getResources().getColorResource(R.color.button_off_white));
        arcProgress.setTextColor(context.getResources().getColorResource(R.color.button_off_white));
        arcProgress.setStrokeWidth(15);
        arcProgress.setUnfinishedStrokeColor(context.getResources().getColorResource(R.color.button_transparent_white));*/
    }

    public static void styleDonut(DonutProgress dProgress, Context context)
    {
        dProgress.setUnfinishedStrokeWidth(2);
        dProgress.setFinishedStrokeWidth(10);
        dProgress.setTextColor(context.getResources().getColor(R.color.button_off_white));
        dProgress.setFinishedStrokeColor(context.getResources().getColor(R.color.button_off_white));
        dProgress.setUnfinishedStrokeColor(context.getResources().getColor(R.color.button_transparent_white));
        dProgress.setInnerBackgroundColor(context.getResources().getColor(R.color.button_transparent));
        //cProgress.setTextSize(8);
        /*arcProgress.setBottomText(context.getString(R.string.progress));
        arcProgress.setFinishedStrokeColor(context.getResources().getColorResource(R.color.button_off_white));
        arcProgress.setTextColor(context.getResources().getColorResource(R.color.button_off_white));
        arcProgress.setStrokeWidth(15);
        arcProgress.setUnfinishedStrokeColor(context.getResources().getColorResource(R.color.button_transparent_white));*/
    }

    public static void styleArc(ArcProgress arcProgress, Context context)
    {
        arcProgress.setBottomText(context.getString(R.string.progress));
        arcProgress.setFinishedStrokeColor(context.getResources().getColor(R.color.button_off_white));
        arcProgress.setTextColor(context.getResources().getColor(R.color.button_off_white));
        arcProgress.setStrokeWidth(15);
        arcProgress.setUnfinishedStrokeColor(context.getResources().getColor(R.color.button_transparent_white));
    }

    public static void showProgress(Context context, PROGRESS_STYLE progress_style)
    {
        mProgressDialog = new ProgressDialog(context);
        int style = (progress_style == PROGRESS_STYLE.INDETERMINATE)?ProgressDialog.STYLE_SPINNER:ProgressDialog.STYLE_HORIZONTAL;

        mProgressDialog.setMessage(Constants.DOWNLOAD_TEXT);
        mProgressDialog.setProgressStyle(style);
        mProgressDialog.setCancelable(false);
        mProgressDialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation_down_up;
        mProgressDialog.show();
    }

   /* public static void updateProgressInDeterminate(String text)
    {
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(text);
    }*/

    public static void updateProgressDeterminate(String text, int value)
    {
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage(text);
        mProgressDialog.setProgress(value);
    }

    public static void hideProgress()
    {
        mProgressDialog.dismiss();
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void setPreference(Context context, String key, String value)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPreference(Context context, String key)
    {
        return getPreference(context, key, null);
    }

    public static String getPreference(Context context, String key, String defaultValue)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(key, defaultValue);
        return value;
    }

    public static void crittercismLog(String data)
    {
        Crittercism.leaveBreadcrumb(data);
    }
    public static void crittercismException(Exception  exception)
    {
        Crittercism.logHandledException(exception);
    }

    public static Object getFromJson(String data, Class clazz)
    {
        Object o = null;

        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            o = gson.fromJson(data, clazz);
        }catch (Exception e){
            crittercismException(e);
            Log.e(TAG, "Json Error" + e.getMessage());
        }

        return o;
    }

    /*public static void lazyload(Context context, ImageView fromview, ImageView toview, String url)
    {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.loading)
                .crossFade()
                .animate(new FlipAnimation(fromview, toview))
                .into(toview);
    }*/

    public static void glideLoadRounded(Context context, ImageView view, String url)
    {
        Glide.with(context)
                .load(url)
                .transform(new CircleTransform(context))
                .sizeMultiplier(1.0f)//fitCenter()
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .animate(new FlipAnimation(view, view))
                .into(view);
    }
    
    public static void glideLoad(Context context, ImageView view, String url)
    {
        Glide.with(context)
                .load(url)
                .sizeMultiplier(1.0f)//fitCenter()
                .placeholder(R.drawable.loading)
                .crossFade()
                .animate(new FlipAnimation(view, view))
                .into(view);
    }

    public static void lazyload(Context context, ImageView view, String url)
    {
        if(url == null || url.length() <= 1) {    // no image
            view.setVisibility(View.GONE);
        }
        else if(url.contains(Constants.HTTP_FLAG)) { // web url
            glideLoad(context, view, url);
        }
        else {  // local image asset or sdcard
            String localUrl = downloadImageExists(url, "").toString();
            File file = new  File(localUrl);
            if(file.exists()){   // sdcard image no extension
                /*Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                view.setImageBitmap(bitmap);*/
                glideLoad(context, view, localUrl);
            }
            else{
                localUrl = downloadImageExists(url, Constants.IMAGE_EXTENSION_JPG).toString();
                file =  new  File(localUrl);
                if(file.exists()){   // sdcard jpeg image

                    glideLoad(context, view, localUrl);
                }
                else
                {
                    localUrl = downloadImageExists(url, Constants.IMAGE_EXTENSION_PNG).toString();
                    file =  new  File(localUrl);
                    if(file.exists()){   // sdcard png image
                        glideLoad(context, view, localUrl);
                    }else {   // asset image
                        localUrl = Utils.getTempString(Constants.BACKSLASH, Constants.DATA_FOLDER, url, Constants.IMAGE_EXTENSION_JPG);
                        //view.setImageResource(getDrawable(context, localUrl));
                        Drawable d = Utils.getDrawableFromAsset(context, localUrl);
                        if(d == null)
                        {   localUrl = Utils.getTempString(Constants.BACKSLASH, Constants.DATA_FOLDER, url, Constants.IMAGE_EXTENSION_PNG);
                            //view.setImageResource(getDrawable(context, localUrl));
                            d = Utils.getDrawableFromAsset(context, localUrl);
                        }

                        view.setImageDrawable(d);
                    }
                }
            }
        }
    }



    public static String downloadImageExists(String url, String extension){

        return Utils.getTempString(Constants.SD_CARD, Constants.DATA_FOLDER, url, extension);

    }

    public static void animateFlip(View rootLayout, View cardFace, View cardBack)
    {
        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.INVISIBLE)
        {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    public static void setText(TextView textView, String data){
        if(data != null && data.length()>1){
            textView.setText(Html.fromHtml(data));
        }
    }

    public static void courseFacade(FragmentActivity activity, String data, MODULE_TYPE module_type)
    {
        //String data = activity.getIntent().getStringExtra(Constants.FRAGMENT_DATA);
        //Constants.MODULE_TYPE module_type = Constants.MODULE_TYPE.valueOf(activity.getIntent().getStringExtra(Constants.FRAGMENT_TYPE));
        Bundle arguments = new Bundle();
        arguments.putString(Constants.FRAGMENT_DATA, data);

        switch (module_type){

            case LESSON: {
                LessonDetailFrag fragment = new LessonDetailFrag();
                fragment.setArguments(arguments);
                activity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.lesson_detail_container, fragment).commit();
                break;
            }case FLASHCARD: {
                FlashcardDetailFrag fragment = new FlashcardDetailFrag();
                fragment.setArguments(arguments);
                activity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.lesson_detail_container, fragment).commit();
                break;
            }case MCQ_QUIZ: {
                MCQDetailFrag fragment = new MCQDetailFrag();
                fragment.setArguments(arguments);
                activity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.lesson_detail_container, fragment).commit();
                break;
            }case ORDER_GAME_QUIZ: {
                OrderGameDetailFragNew fragment = new OrderGameDetailFragNew();
                fragment.setArguments(arguments);
                activity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.lesson_detail_container, fragment).commit();
                break;
            }case PICTURE_MATCH_QUIZ: {
                PictureMatchDetailFrag fragment = new PictureMatchDetailFrag();
                fragment.setArguments(arguments);
                activity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.lesson_detail_container, fragment).commit();
                break;
            }case SIMPLE_QUIZ: {
                SimpleQuizDetailFrag fragment = new SimpleQuizDetailFrag();
                fragment.setArguments(arguments);
                activity.getSupportFragmentManager().beginTransaction()
                        .add(R.id.lesson_detail_container, fragment).commit();
                break;
            }

        }
    }

    public static int getColorResource(Context context, int colorId)
    {
        return context.getResources().getColor(colorId);
    }

    public static String getStringResource(Context context, int stringId)
    {
        return context.getResources().getString(stringId);
    }

    public static String getTempString(String... args)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str:args){
            stringBuilder.append(str);
        }

        return stringBuilder.toString();
    }

    public static String getTempStringNewLine(String... args)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str:args){
            stringBuilder.append(str);
            stringBuilder.append(Constants.NEWLINE);
        }

        return stringBuilder.toString();
    }

    public static StringBuilder getTempStringBuilder(String... args)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str:args){
            stringBuilder.append(str);
        }

        return stringBuilder;
    }


    public static  void dirChecker(String location, String dir) {
        File f = new File(location + dir);

        if(!f.isDirectory()) {
            Log.v(TAG, " created dir: "+dir);
            f.mkdirs();
        }
    }


    public static void makeDirs(Context context, String path)
    {
        String dirs[] = path.split(Constants.BACKSLASH);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        Log.v(TAG, "Creating download directory..."+ path);
        for(String str:dirs)
        {
            if(!str.contains(".") && str.length()>0){
                Utils.dirChecker(stringBuffer.toString(), str);
                stringBuffer.append(str);
            }

        }

        refreshFileSystem(context, path);
    }

    public static void refreshFileSystem(Context context, String path)
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File file = new File(path);
            Uri contentUri = Uri.fromFile(file); //out is your output file
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
        } else {
            context.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://"
                            + Environment.getExternalStorageDirectory())));
        }
    }

    public static void firstRun(Context context)
    {
        if(getPreference(context, Constants.FIRST_RUN_KEY) == null){
            setPreference(context, Constants.FIRST_RUN_KEY, Constants.FIRST_RUN_KEY);
            Utils.handleInfo(context, "First run detected");
          /*  makeDirs(context, getTempString(Constants.SD_CARD, Constants.DATA_FOLDER));
            refreshFileSystem(context, getTempString(Constants.SD_CARD, Constants.DATA_FOLDER));*/
        }
    }

    public static void sendMail(Context context, String string)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", Constants.EMAIL, null));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.ERROR_INVALID_JSON);
        emailIntent.putExtra(Intent.EXTRA_TEXT, string+"\n--------\n\n\n\n-------\n");
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }


    public static void mail(Context context, String data)
    {
        mail(context, "ir2pid@gmail.com", data);
    }

    public static void mail(Context context, String to, String data)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", to, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "schema");
        emailIntent.putExtra(Intent.EXTRA_TEXT, data+"\n--------\n\n\n\n-------\n");
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public static void backPress(Activity activity)
    {
        activity.onBackPressed();
    }

    public static ArrayList<IntegerStringPair>map2IntegerStringPairList(Map<Integer, String> map){
        ArrayList<IntegerStringPair> integerStringPairList = new ArrayList<IntegerStringPair>();

        for (Map.Entry<Integer, String> entry : map.entrySet())
        {
            IntegerStringPair integerStringPair = new IntegerStringPair(entry.getKey(), entry.getValue());
            integerStringPairList.add(integerStringPair);
        }

        return integerStringPairList;
    }

    public static void buttonActivate(Button button){
        button.setEnabled(true);
        button.setBackgroundResource(R.drawable.button_green);
    }

    public static void buttonDeactivate(Button button){
        button.setEnabled(false);
        button.setBackgroundResource(R.drawable.button_grey);
    }
}
