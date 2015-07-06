package com.noisyninja.abheda_droid.pojo;

import com.noisyninja.abheda_droid.util.Constants.MODULE_TYPE;
/**
 * Created by ir2pi on 1/17/2015.
 */
public class BaseLesson extends BasePojo{
    MODULE_TYPE module_type;

    public MODULE_TYPE getModule_type() {
        return module_type;
    }

    public void setModule_type(MODULE_TYPE module_type) {
        this.module_type = module_type;
    }
}
