package com.noisyninja.abheda_droid.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.fragment.PictureMatchDetailFrag;
import com.noisyninja.abheda_droid.util.Constants;

/**
 * Created by ir2pi on 12/7/2014.
 */
public class QuizDetailActivityDeprecated extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_detail);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(Constants.FRAGMENT_DATA, getIntent()
                    .getStringExtra(Constants.FRAGMENT_DATA));

            /*if(Math.random() > 0.3) {
                MCQDetailFrag fragment = new MCQDetailFrag();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.quiz_detail_container, fragment).commit();
            }
            else  if(Math.random() > 0.3 && Math.random() < 0.6)
            {
                OrderGameDetailFrag fragment = new OrderGameDetailFrag();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.quiz_detail_container, fragment).commit();
            }
            else
            {*/
                PictureMatchDetailFrag fragment = new PictureMatchDetailFrag();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.quiz_detail_container, fragment).commit();
            //}
        }
    }
}
