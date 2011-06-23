package com.example;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/19/11
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyHorizontalScrollView extends HorizontalScrollView {
    public MyHorizontalScrollView(Context c) {
        super(c);
    }

    public MyHorizontalScrollView(Context context, AttributeSet set) {
        super(context, set);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!Config.scrollOn)
            return false;

        return super.onInterceptTouchEvent(ev);

    }
}
