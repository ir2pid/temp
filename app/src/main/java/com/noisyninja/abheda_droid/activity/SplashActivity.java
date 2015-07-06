package com.noisyninja.abheda_droid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.crittercism.app.Crittercism;
import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.Utils;

/**
 * Created by ir2pi on 12/5/2014.
 */

public class SplashActivity extends Activity {

    private static String TAG = SplashActivity.class.getName();
    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;
    Context context;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SplashActivity splashActivity = this;
        // Splash screen view
        setContentView(R.layout.splash_activity);
        context = this;
        final ImageView splashImageView = (ImageView) findViewById(R.id.SplashImageView);
        splashImageView.setBackgroundResource(R.drawable.splashframes);
        final AnimationDrawable frameAnimation = (AnimationDrawable)splashImageView.getBackground();

        splashImageView.post(new Runnable(){
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        Utils.firstRun(context);
        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        Crittercism.initialize(getApplicationContext(), Constants.CRITTERCISM_APP_ID);
                        wait(Constants.SLEEP_TIME_1000);
                    }
                }
                catch(InterruptedException ex){
                }

                finish();

                // Run next activity
                Intent intent = new Intent();
                intent.setClass(splashActivity, MainActivity.class);
                startActivity(intent);

            }
        };

        mSplashThread.start();
    }

    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        return true;
    }
}