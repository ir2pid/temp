package com.noisyninja.abheda_droid.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.noisyninja.abheda_droid.R;
import com.noisyninja.abheda_droid.control.carousel.AppUtils;
import com.noisyninja.abheda_droid.control.carousel.CarouselDataItem;
import com.noisyninja.abheda_droid.control.carousel.CarouselView;
import com.noisyninja.abheda_droid.control.carousel.CarouselViewAdapter;
import com.noisyninja.abheda_droid.control.carousel.Singleton;

import java.util.ArrayList;

/**
 * Created by ir2pi on 2/14/2015.
 */
public class CarouselFrag extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher {

    Singleton m_Inst 					= Singleton.getInstance();
    CarouselViewAdapter 	m_carouselAdapter		= null;
    private final int		m_nFirstItem			= 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //no keyboard unless requested by user
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // compute screen size and scaling
        Singleton.getInstance().InitGUIFrame(getActivity());

        int padding = m_Inst.Scale(10);
        // create the interface : full screen container
        RelativeLayout panel  = new RelativeLayout(getActivity());
        panel.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT));
        panel.setPadding(padding, padding, padding, padding);
        panel.setBackgroundDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.WHITE, Color.GRAY}));
        //setContentView(panel);

        // copy images from assets to sdcard
        AppUtils.AssetFileCopy(getActivity(), "/mnt/sdcard/comic1.jpg", "abheda/comic1.jpg", false);
        AppUtils.AssetFileCopy(getActivity(), "/mnt/sdcard/comic2.jpg", "abheda/comic2.jpg", false);
        //AppUtils.AssetFileCopy(getActivity(), "/mnt/sdcard/imageholder.png", "comic1.png", false);
        //AppUtils.AssetFileCopy(getActivity(), "/mnt/sdcard/imageholder.png", "imageholder.png", false);

        //Create carousel view documents
        ArrayList<CarouselDataItem> Docus = new ArrayList<CarouselDataItem>();
        for (int i=0;i<1000;i++) {
            CarouselDataItem docu;
            if (i%4==0) docu = new CarouselDataItem("/mnt/sdcard/comic1.jpg", 0, "First Image "+i);
            else if (i%4==1) docu = new CarouselDataItem("/mnt/sdcard/comic2.jpg", 0, "Second Image "+i);
            else if (i%4==2) docu = new CarouselDataItem("/mnt/sdcard/comic1.jpg", 0, "Third Image "+i);
            else docu = new CarouselDataItem("/mnt/sdcard/comic2.jpg", 0, "4th Image "+i);
            Docus.add(docu);
        }

        // add the serach filter
        EditText etSearch = new EditText(getActivity());
        etSearch.setHint("Search your documents");
        etSearch.setSingleLine();
        etSearch.setTextColor(Color.BLACK);
        etSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_disclosure, 0, 0, 0);
        AppUtils.AddView(panel, etSearch, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,
                new int[][]{new int[]{RelativeLayout.CENTER_HORIZONTAL}, new int[]{RelativeLayout.ALIGN_PARENT_TOP}}, -1,-1);
        etSearch.addTextChangedListener((TextWatcher) this);

        // add logo
        TextView tv = new TextView(getActivity());
        tv.setTextColor(Color.BLACK);
        tv.setText("www.pocketmagic.net");
        AppUtils.AddView(panel, tv, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,
                new int[][]{new int[]{RelativeLayout.CENTER_HORIZONTAL}, new int[]{RelativeLayout.ALIGN_PARENT_BOTTOM}}, -1,-1);

        // create the carousel
        CarouselView coverFlow = new CarouselView(getActivity());

        // create adapter and specify device independent items size (scaling)
        // for more details see: http://www.pocketmagic.net/2013/04/how-to-scale-an-android-ui-on-multiple-screens/
        m_carouselAdapter =  new CarouselViewAdapter(getActivity(), Docus, m_Inst.Scale(600), m_Inst.Scale(400));
        coverFlow.setAdapter(m_carouselAdapter);
        coverFlow.setSpacing(-1*m_Inst.Scale(300));
        coverFlow.setSelection(Integer.MAX_VALUE / 2, true);
        coverFlow.setAnimationDuration(1000);
        coverFlow.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        AppUtils.AddView(panel, coverFlow, RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT,
                new int[][]{new int[]{RelativeLayout.CENTER_IN_PARENT}},
                -1, -1);

        return panel;
    }

    public void afterTextChanged(Editable arg0) {}

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        m_carouselAdapter.getFilter().filter(s.toString());
    }

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        CarouselDataItem docu =  (CarouselDataItem) m_carouselAdapter.getItem((int) arg3);
        if (docu!=null)
            Toast.makeText(getActivity(), "You've clicked on:" + docu.getDocText(), Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {}


}
