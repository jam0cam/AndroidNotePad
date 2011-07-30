package com.jia.tabpadapp.main;

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
    public Point start;
    public Point end;

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
        draw(c, 0, 0);
    }

    @Override
    public void draw(Canvas c, int xOffset, int yOffset) {
        if (start.equals(end)) {
            start.draw(c, xOffset, yOffset);
        } else {
            Paint p = new Paint(ApplicationState.paint);
            p.setColor(start.color);
            p.setStrokeWidth(start.strokeWidth);
            c.drawLine(start.x + xOffset, start.y + yOffset, end.x + xOffset, end.y + yOffset, p);
        }
    }
}
