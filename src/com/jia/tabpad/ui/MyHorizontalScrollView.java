package com.jia.tabpad.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import com.jia.tabpad.main.ApplicationState;

/**
 * This is a custom implementation of a horizontal scroll view.  Basically just wanted to
 * override the Touch method to determine whether it is a scroll, or a painting.
 * 
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


    /**
     * If the canvas is in a SCROLLING mode, then process the event.  Otherwise, the user is trying
     * to draw, so ignore this event.
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ApplicationState.canvasMode != ApplicationState.CanvasMode.SCROLLING)
            return false;

        return super.onInterceptTouchEvent(ev);

    }
}
