package com.noisyninja.abheda_droid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noisyninja.abheda_droid.R;

/**
 * Created by ir2pi on 12/12/2014.
 */
public class MotherModuleFrag extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View windows = inflater.inflate(R.layout.frag_mother_module, container, false);

        /*DraggableGridView draggableGridView = ((DraggableGridView)windows.findViewById(R.id.draggablegrid1));

        for(int i=0;i<5;i++)
        {
            TextView button = new TextView(getActivity());
            button.setText("button"+(i+1));
            draggableGridView.addView(button);
        }*/

        return windows;
    }
}
