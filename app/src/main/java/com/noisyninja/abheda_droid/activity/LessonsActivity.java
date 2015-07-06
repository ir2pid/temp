package com.noisyninja.abheda_droid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.fragment.LessonListFrag;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.Constants.MODULE_TYPE;
import com.noisyninja.abheda_droid.util.Utils;

/**
 * Created by ir2pi on 12/3/2014.
 */
public class LessonsActivity extends FragmentActivity implements
        LessonListFrag.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lessons);
        context = this;
        if (findViewById(R.id.lesson_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((LessonListFrag) getSupportFragmentManager().findFragmentById(
                    R.id.lesson_list_frag)).setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link LessonListFrag.Callbacks} indicating that
     * the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(MODULE_TYPE module_type, String data) {

        Utils.playSound(this, Constants.Sound.CLICK);
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Utils.courseFacade(this, data, module_type);
            /*Bundle arguments = new Bundle();
            arguments.putString(Constants.FRAGMENT_DATA, data);
            LessonDetailFrag fragment = new LessonDetailFrag();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.lesson_detail_container, fragment).commit();*/

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, LessonDetailActivity.class);
            detailIntent.putExtra(Constants.FRAGMENT_DATA, data);
            detailIntent.putExtra(Constants.FRAGMENT_TYPE, module_type.toString());
            startActivity(detailIntent);
           /* switch(module_type)
            {
                case LESSON:{

                    break;
                }
                case MCQ_QUIZ:{
                    Intent detailIntent = new Intent(this, LessonDetailActivity.class);
                    detailIntent.putExtra(Constants.FRAGMENT_DATA, data);
                    detailIntent.putExtra(Constants.FRAGMENT_TYPE, module_type);
                    startActivity(detailIntent);
                }
                default:Utils.handleInfo(context, Constants.INFO_NO_LESSON);
            }*/

        }
    }
}