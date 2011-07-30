package com.jia.tabpadapp.main;

import android.graphics.Canvas;

/**
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/15/11
 * Time: 11:33 PM
 */
public interface Drawable {
    void draw(Canvas c);
    void draw(Canvas c, int xOffset, int yOffset);
}
