package com.jia.tabpad.main;

import android.graphics.Canvas;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/15/11
 * Time: 11:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Stroke implements Drawable, Serializable {
    public ArrayList<Line> lines = new ArrayList<Line>();

    public void addLine(Line l) {
        lines.add(l);
    }

    @Override
    public void draw(Canvas c) {
        draw(c, 0, 0);
    }

    @Override
    public void draw(Canvas c, int xOffset, int yOffset) {
        for (Line l : lines) {
            l.draw(c, xOffset, yOffset);
        }
    }
}
