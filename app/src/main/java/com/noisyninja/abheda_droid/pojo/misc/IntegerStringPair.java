package com.noisyninja.abheda_droid.pojo.misc;

import com.noisyninja.abheda_droid.pojo.BasePojo;

/**
 * Created by ir2pi on 4/28/2015.
 */
public class IntegerStringPair extends BasePojo{

    int i;
    String s;
    public IntegerStringPair(int i, String s){
        this.i = i;
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }
}
