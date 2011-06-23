package com.example;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/15/11
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Point implements Drawable, Serializable {
    float x, y;
    int color;
    int strokeWidth;

    /**
     *
     * @param x coordinate
     * @param y coordinate
     * @param p paint
     */
    public Point(float x, float y, Paint p) {
        this.x = x;
        this.y = y;
        this.color = p.getColor();
        this.strokeWidth = Math.round(p.getStrokeWidth());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean equals(Point p) {
        if (this.x == p.x && this.y == p.y)
            return true;
        else
            return false;
    }

    @Override
    public void draw(Canvas c) {
        Paint p = new Paint();
        p.setColor(this.color);
        p.setStrokeWidth(this.strokeWidth);
        c.drawPoint(x, y, p);
    }
}
