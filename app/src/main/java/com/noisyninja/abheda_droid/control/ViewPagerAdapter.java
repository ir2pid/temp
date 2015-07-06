package com.noisyninja.abheda_droid.control;

/**
 * Created by ir2pi on 2/17/2015.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.pojo.Page;
import com.noisyninja.abheda_droid.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    List<Page> pageArrayList;
    LayoutInflater inflater;
    boolean isBack;
    Map<Integer, ViewFlipper> containerMap = new HashMap<Integer, ViewFlipper>();

    public ViewPagerAdapter(Context context, List<Page> pageArrayList) {
        this.context = context;
        this.pageArrayList = pageArrayList;
        isBack = false;
    }

    @Override
    public int getCount() {
        return pageArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        // Declare Variables
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = loadView(container, position);
        itemView.setTag(position);
        ((ViewPager) container).addView(itemView);

        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.makeToast(context, "clicked: "+position);
                int pos = (Integer)view.getTag();
                //View itemView = loadView(containerMap.get(Integer.valueOf(position)), position, false);

                //Utils.animateFlip(view.getRootView(), view, view);
            }
        });*/
        return itemView;
    }

    private View loadView(ViewGroup container, int position)
    {
        TextView name1;
        TextView description1;
        TextView text1;
        ImageView image1;
        TextView name2;
        TextView description2;
        TextView text2;
        ImageView image2;

        View itemView = inflater.inflate(R.layout.item_view_pager, container,
                false);
        itemView.setTag(position);

        name1 = (TextView) itemView.findViewById(R.id.name1);
        description1 = (TextView) itemView.findViewById(R.id.description1);
        text1 = (TextView) itemView.findViewById(R.id.text1);
        image1 = (ImageView) itemView.findViewById(R.id.image1);

        Utils.setText(name1,pageArrayList.get(position).getName());
        Utils.setText(description1,pageArrayList.get(position).getDescription());
        Utils.setText(text1,pageArrayList.get(position).getText1());
        Utils.lazyload(context, image1, pageArrayList.get(position).getImage1());


        ViewFlipper viewSwitcher = (ViewFlipper) itemView.findViewById(R.id.viewflipper);
        containerMap.put(position, viewSwitcher);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (Integer)view.getTag();
                ViewFlipper viewSwitcher = containerMap.get(pos);
                if(!isBack) {
                    viewSwitcher.showNext();
                    isBack = true;
                }else {
                    viewSwitcher.showPrevious();
                    isBack = false;
                }
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }
}