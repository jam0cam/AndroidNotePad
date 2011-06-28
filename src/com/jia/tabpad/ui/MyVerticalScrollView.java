package com.jia.tabpad.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;
import com.jia.tabpad.main.ApplicationState;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/19/11
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyVerticalScrollView extends ScrollView {

    public MyVerticalScrollView(Context c) {
        super (c);
    }

    public MyVerticalScrollView(Context context, AttributeSet set) {
        super(context,  set);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ApplicationState.canvasMode != ApplicationState.CanvasMode.SCROLLING)
            return false;

        return super.onInterceptTouchEvent(ev);

    }

}
