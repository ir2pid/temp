package com.noisyninja.abheda_droid.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.Constants.MODULE_TYPE;
import com.noisyninja.abheda_droid.util.Utils;

/**
 * Created by ir2pi on 12/6/2014.
 */
public class LessonDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_detail);

        // Not available in legacy apps
        // Show the Up button in the action bar.
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            String data = getIntent().getStringExtra(Constants.FRAGMENT_DATA);
            MODULE_TYPE module_type = MODULE_TYPE.valueOf(getIntent().getStringExtra(Constants.FRAGMENT_TYPE));

            Utils.courseFacade(this, data, module_type);
            /*Bundle arguments = new Bundle();
            arguments.putString(Constants.FRAGMENT_DATA, data);

            switch (module_type){

                case LESSON: {
                    LessonDetailFrag fragment = new LessonDetailFrag();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.lesson_detail_container, fragment).commit();
                    break;
                }case FLASHCARD: {
                    FlashcardDetailFrag fragment = new FlashcardDetailFrag();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.lesson_detail_container, fragment).commit();
                    break;
                }case MCQ_QUIZ: {
                    MCQDetailFrag fragment = new MCQDetailFrag();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.lesson_detail_container, fragment).commit();
                    break;
                }case ORDER_GAME_QUIZ: {
                    OrderGameDetailFrag fragment = new OrderGameDetailFrag();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.lesson_detail_container, fragment).commit();
                    break;
                }case PICTURE_MATCH_QUIZ: {
                    PictureMatchDetailFrag fragment = new PictureMatchDetailFrag();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.lesson_detail_container, fragment).commit();
                    break;
                }

            }*/

        }
    }
/*
 *not available in legacy apps
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                case android.R.id.home:
                        // This ID represents the Home or Up button. In the case of this
                        // activity, the Up button is shown. Use NavUtils to allow users
                        // to navigate up one level in the application structure. For
                        // more details, see the Navigation pattern on Android Design:
                        //
                        // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                        //
                        NavUtils.navigateUpTo(this, new Intent(this,
                                        CircleListActivity.class));
                        return true;
                }
                return super.onOptionsItemSelected(item);
        }*/
}