package com.noisyninja.abheda_droid.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.activity.CourseActivity;
import com.noisyninja.abheda_droid.control.AnimatedButton;
import com.noisyninja.abheda_droid.pojo.Topic;
import com.noisyninja.abheda_droid.pojo.Topics;
import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.DataStore;
import com.noisyninja.abheda_droid.util.DownloadFileAsync;
import com.noisyninja.abheda_droid.util.IDownloadFileAsyncCallback;
import com.noisyninja.abheda_droid.util.Utils;

import java.util.ArrayList;

/**
 * Created by ir2pi on 12/5/2014.
 */
public class TopicsGridFrag extends Fragment implements View.OnClickListener, IDownloadFileAsyncCallback {

    ArrayList<Topic> topicArrayList;
    GridView gridView;
    ArcProgress arcProgress;

    AnimatedButton animatedButton1;
    private CustomAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View windows = inflater.inflate(R.layout.frag_topics_grid, container, false);
        animatedButton1 = (AnimatedButton) windows.findViewById(R.id.button1);
        animatedButton1.setOnClickListener(this);

        gridView = (GridView) windows.findViewById(R.id.gridview1);
        arcProgress = (ArcProgress) windows.findViewById(R.id.arc_progress_total);
        arcProgress.setProgress(15);
        Utils.styleArc(arcProgress,getActivity());
        Topics topics = DataStore.getInstance(getActivity()).getTopics();
        topicArrayList = topics.getTopics();

        adapter = new CustomAdapter(getActivity(),topicArrayList);

        gridView.setAdapter(adapter);

        return windows;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1: {
                String url = Utils.getPreference(getActivity(), Constants.URL_STORE_KEY, Constants.URL_STORE);

                Utils.handleInfo(getActivity(), Constants.DOWNLOAD_TEXT +" "+url);
                String[] params = {url,
                        Utils.getTempString(Constants.SD_CARD, Constants.DATA_ZIP),
                        Utils.getTempString(Constants.SD_CARD, Constants.DATA_FOLDER)};

               // Utils.makeDirs(getActivity(), params[1]);

                new DownloadFileAsync(this, getActivity()).execute(params);

                break;
            }
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

        DataStore.init(getActivity());
        Topics topics = DataStore.getInstance(getActivity()).getTopics();
        topicArrayList = topics.getTopics();

        adapter = new CustomAdapter(getActivity(),topicArrayList);
        gridView.invalidateViews();
        gridView.setAdapter(adapter);

        Utils.handleInfo(getActivity(), Constants.INFO_SUCCESS_DOWNLOAD);

    }

    public class CustomAdapter extends BaseAdapter {

        ArrayList<Topic> topicArrayList;
        Context context;
        private LayoutInflater inflater=null;

        public CustomAdapter(Activity activity, ArrayList<Topic> topicArrayList) {
            // TODO Auto-generated constructor stub
            context = activity;
            this.topicArrayList = topicArrayList;
            inflater = ( LayoutInflater )context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return topicArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder
        {
            TextView tName;
            TextView tDescription;
            DonutProgress dProgressCompletion;
            TextView tMarks;
            ImageView img;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=new Holder();
            View rowView;

            rowView = inflater.inflate(R.layout.topics_grid_item, null);
            holder.tName =(TextView) rowView.findViewById(R.id.textView1);
            holder.tDescription =(TextView) rowView.findViewById(R.id.textView2);
            holder.dProgressCompletion =(DonutProgress) rowView.findViewById(R.id.course_progress_arc);
            holder.tMarks =(TextView) rowView.findViewById(R.id.textView4);

            //holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

            Utils.setText(holder.tName, topicArrayList.get(position).getName());
            Utils.setText(holder.tDescription,topicArrayList.get(position).getDescription());
            holder.dProgressCompletion.setProgress(topicArrayList.get(position).getCompletion());
            Utils.styleDonut(holder.dProgressCompletion, getActivity());
                    //setText(getActivity().getString(R.string.completion)+topicArrayList.get(position).getCompletion());
            holder.tMarks.setText(getActivity().getString(R.string.marks)+topicArrayList.get(position).getMarks());

            //holder.img.setImageResource(imageId[position]);

            rowView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //Toast.makeText(context, "You Clicked "+position, Toast.LENGTH_LONG).show();
                    Constants.TOPIC_ID = position;
                    Utils.playSound(getActivity(), Constants.Sound.CLICK);
                    getLessonKind();
                }
            });

            return rowView;
        }

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
                Utils.startActivity(getActivity(), CourseActivity.class);
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
                Toast.makeText(getActivity(), "Coming soon", Toast.LENGTH_SHORT).show();
                //MainActivity mainActivity = (MainActivity) getActivity();
                //mainActivity.switchTab(2);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}



