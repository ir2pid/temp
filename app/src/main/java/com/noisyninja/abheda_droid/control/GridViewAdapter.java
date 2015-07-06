package com.noisyninja.abheda_droid.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.pojo.misc.IntegerStringPair;

import java.util.ArrayList;

/**
 * Created by ir2pi on 4/23/2015.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<IntegerStringPair> wordsList;

    public GridViewAdapter(Context context, ArrayList<IntegerStringPair> wordsList) {
        this.context = context;
        this.wordsList = wordsList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_order_game_detail, null);

            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView1);
            holder.textView.setText(wordsList.get(position).getS());
            convertView.setTag(holder);
        }

        else{
            holder = (ViewHolder) convertView.getTag();
        }

        /*holder.textView.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg1)
            {
                // list.remove(position);
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });*/

        return convertView;
    }

    @Override
    public int getCount() {
        return wordsList.size();
    }

    @Override
    public Object getItem(int position) {
        return wordsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public static class ViewHolder
    {
        TextView textView;
    }
}
