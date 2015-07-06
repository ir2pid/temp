package com.noisyninja.abheda_droid.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.util.Utils;

import java.util.List;

/**
 * Created by ir2pi on 12/7/2014.
 */
public class ListLessonAdapter  extends ArrayAdapter<ListLessonItem> {

    public ListLessonAdapter(Context context, List<ListLessonItem> items) {
        super(context, R.layout.list_lesson_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        //if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_lesson_item, parent, false);

            TextView name;
            TextView description;
            name = (TextView) convertView.findViewById(R.id.textView1);
            description = (TextView) convertView.findViewById(R.id.textView2);

            /*viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.quiz = (Button) convertView.findViewById(R.id.button1);
            viewHolder.learn = (Button) convertView.findViewById(R.id.button2);*/
            //convertView.setTag(viewHolder);
        //} else {
            // recycle the already inflated view
        //    viewHolder = (ViewHolder) convertView.getTag();
        //}

        // update the item view
        ListLessonItem item = getItem(position);

        Utils.setText(name,item.title);

        Utils.setText(description,item.description);

        RelativeLayout relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout1);
        switch (item.module_type)
        {
            case LESSON:
                relativeLayout.setBackgroundResource(R.drawable.background_shape_green);
                break;
            case FLASHCARD:
                relativeLayout.setBackgroundResource(R.drawable.background_shape_purple);
                break;
            case MCQ_QUIZ:
                relativeLayout.setBackgroundResource(R.drawable.background_shape_red);
                break;
            case PICTURE_MATCH_QUIZ:
                relativeLayout.setBackgroundResource(R.drawable.background_shape_red);
                break;
            case ORDER_GAME_QUIZ:
                relativeLayout.setBackgroundResource(R.drawable.background_shape_red);
                break;
            case SIMPLE_QUIZ:
                relativeLayout.setBackgroundResource(R.drawable.background_shape_blue);
                break;
            default:
                relativeLayout.setBackgroundResource(R.drawable.background_shape_lime);

        }
        /*viewHolder.ivIcon.setImageDrawable(item.icon);

        /*viewHolder.quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.playSound(getContext(), Constants.Sound.CLICK);
                Toast.makeText(getContext(),"quiz selected",Toast.LENGTH_SHORT).show();
                Utils.startActivity(getContext(), QuizDetailActivity.class);

            }
        });
        viewHolder.learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.playSound(getContext(), Constants.Sound.CLICK);
                Toast.makeText(getContext(),"learn selected",Toast.LENGTH_SHORT).show();
                Utils.startActivity(getContext(), LessonDetailActivity.class);
            }
        });*/

        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * @see http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private static class ViewHolder {
        //ImageView ivIcon;
        //Button quiz;
        //Button learn;
        //MODULE_TYPE module_type;
    }
}