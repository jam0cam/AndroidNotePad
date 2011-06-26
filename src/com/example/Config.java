package com.example;

import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.StaticLayout;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import org.apache.http.impl.io.ContentLengthInputStream;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/18/11
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    public static final int DEFAULT_FORE_COLOR = Color.BLUE;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    public static final int CANVAS_WIDTH = 3000;
    public static final int CANVAS_HEIGHT = 3000;
    public static final int DEFAULT_STROKE_WIDTH = 5;
//    public static final int STROKE_PICKER_WIDTH = 500;
//    public static final int STROKE_PICKER_HEIGHT = 500;

    public static final int SEEKBAR_WIDTH = 500;
    public static final int SEEKBAR_HEIGHT = LinearLayout.LayoutParams.WRAP_CONTENT;
    public static final int SEEKBAR_TEXT_WIDTH = LinearLayout.LayoutParams.WRAP_CONTENT;
    public static final int SEEKBAR_TEXT_HEIGHT = 75;
}
