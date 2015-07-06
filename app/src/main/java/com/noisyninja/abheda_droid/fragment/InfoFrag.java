package com.noisyninja.abheda_droid.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.control.AnimatedButton;
import com.noisyninja.abheda_droid.control.SeekArc;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.Utils;

import at.markushi.ui.CircleButton;

/**
 * Created by ir2pi on 11/30/2014.
 */
public class InfoFrag extends Fragment implements ISyncFrag{

    int progress;
    SeekArc seekArc;
    TextView progressText;
    EditText urlText;
    Activity activity;
    CircleButton circleButton1;
    CircleButton circleButton2;
    AnimatedButton continueButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View windows = inflater.inflate(R.layout.frag_info, container, false);
        seekArc = ((SeekArc)windows.findViewById(R.id.seekArc));
        progressText = ((TextView)windows.findViewById(R.id.progressText));
        seekArc.setTouchable(false);
        urlText = ((EditText)windows.findViewById(R.id.urlEdit));
        /*circleButton1 = (CircleButton)windows.findViewById(R.id.circleButton1);
        circleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.playSound(getActivity(), Constants.Sound.CLICK);
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.switchTab(2);
            }
        });
        circleButton2 = (CircleButton)windows.findViewById(R.id.circleButton2);
        circleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Utils.playSound(getActivity(), Constants.Sound.CLICK);
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.switchTab(0);
            }
        });*/
        urlText.setText(Utils.getPreference(getActivity(), Constants.URL_STORE_KEY));
        urlText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Utils.handleInfo(getActivity(), urlText.getText().toString());
                    Utils.setPreference(getActivity(), Constants.URL_STORE_KEY, urlText.getText().toString());
                    return true;
                }
                return false;
            }
        });
        continueButton = (AnimatedButton)windows.findViewById(R.id.button3);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.handleInfo(getActivity(), urlText.getText().toString());
                Utils.setPreference(getActivity(), Constants.URL_STORE_KEY, urlText.getText().toString());
                //getLessonKind();
            }
        });
        return windows;
    }

    public void getLessonKind()
    {
        // custom dialog
        final Dialog dialog = new Dialog(getActivity(), R.style.TransparentDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_course_type);
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation_up_down;
        //dialog.setTitle("Select course type...");

        // set the custom dialog components - button
        AnimatedButton buttonModule = (AnimatedButton) dialog.findViewById(R.id.button1);
        buttonModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Utils.playSound(getActivity(), Constants.Sound.CLICK);
                Toast.makeText(getActivity(), "Module selected", Toast.LENGTH_SHORT).show();
                //MainActivity mainActivity = (MainActivity) getActivity();
                //mainActivity.switchTab(0);
                dialog.dismiss();
            }
        });

        AnimatedButton buttonRandom = (AnimatedButton) dialog.findViewById(R.id.button2);
        buttonRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Utils.playSound(getActivity(), Constants.Sound.CLICK);
                Toast.makeText(getActivity(), "Random selected", Toast.LENGTH_SHORT).show();
                //MainActivity mainActivity = (MainActivity) getActivity();
                //mainActivity.switchTab(2);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    public void onAttach(Activity activity)
    {
        progress = 0;
        super.onAttach(activity);
        ProgressTask progressTask = new ProgressTask();
        progressTask.execute(new Activity[]{activity});

    }

    public void sync(int value){
        if(seekArc!=null)
        seekArc.setProgress(value);
    }

    private class ProgressTask extends AsyncTask<Activity, Integer, String> {
        Activity activity;
        @Override
        protected String doInBackground(Activity... params) {
            activity = params[0];
            while(progress < Constants.PROGRESS) {
                try {
                    Thread.sleep(Constants.SLEEP_TIME_20);
                    progress++;
                    onProgressUpdate(new Integer[]{progress});
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(final Integer... values) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    seekArc.setProgress(values[0]);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(Constants.PROGRESS_TEXT);
                    stringBuilder.append(values[0]);
                    stringBuilder.append(Constants.PROGRESS_PERCENT);
                    progressText.setText(stringBuilder.toString());
                }
            });
        }
    }
}