package com.noisyninja.abheda_droid.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.noisyninja.abheda_droid.util.Constants;
import com.noisyninja.abheda_droid.util.Utils;

/**
 * Created by ir2pi on 1/4/2015.
 */
public class AnimatedButton extends Button implements View.OnClickListener{

    Context context;
    View v;

    public AnimatedButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public AnimatedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public AnimatedButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init(){
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Do something
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:{
                Utils.playSound(context, Constants.Sound.CLICK);
                Utils.makeAnimation(this);
                break;
            }
            default:
                break;

        }

        return super.onTouchEvent(event);

    }
}
