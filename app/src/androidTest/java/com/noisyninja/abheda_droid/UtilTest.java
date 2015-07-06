package com.noisyninja.abheda_droid;

import android.test.InstrumentationTestCase;

/**
 * Created by ir2pi on 12/3/2014.
 */
public class UtilTest extends InstrumentationTestCase {

    public void test() throws Exception {
        final int expected = 1;
        final int reality = 5;
        assertEquals(expected, reality);
    }
}
