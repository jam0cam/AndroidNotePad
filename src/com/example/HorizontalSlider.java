package com.example;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Map;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/21/11
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class HorizontalSlider extends ProgressBar {

        private OnProgressChangeListener listener;

        private static int padding = 2;

        public interface OnProgressChangeListener {
                void onProgressChanged(View v, int progress);
        }

        public HorizontalSlider(Context context, AttributeSet attrs) {
                super(context, attrs);
                init();
        }

        public HorizontalSlider(Context context) {
                super(context);
            init();

        }

        private void init() {
                        this.setMax(10);
        }
        public void setOnProgressChangeListener(OnProgressChangeListener l) {
                listener = l;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN
                                || action == MotionEvent.ACTION_MOVE) {
                        float x_mouse = event.getX() - padding;
                        float width = getWidth() - 2*padding;
                        int progress = Math.round((float) getMax() * (x_mouse / width));

                        if (progress < 0)
                                progress = 0;

                        this.setProgress(progress);

                        if (listener != null)
                                listener.onProgressChanged(this, progress);

                }

                return true;
        }
}
