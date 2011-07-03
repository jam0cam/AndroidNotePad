package com.jia.tabpad.main;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

/**
 * This is a line.  Knows how to draw itself on the canvas.
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/15/11
 * Time: 11:35 PM
 */
public class Line implements Drawable, Serializable {
    Point start;
    Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[" + "(" + start.x + ", " + start.y + ")," + "(" + end.x + ", " + end.y + ")" + "]";
    }

    @Override
    public void draw(Canvas c) {
        if (start.equals(end)) {
            start.draw(c);
        } else {
            Paint p = new Paint(ApplicationState.paint);
            p.setColor(start.color);
            p.setStrokeWidth(start.strokeWidth);
            c.drawLine(start.x, start.y, end.x, end.y, p);
        }

    }
}
