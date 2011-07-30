package com.jia.tabpadapp.main;

import android.graphics.Bitmap;
import android.graphics.Paint;

/**
 * Contains the state of the application.
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/23/11
 * Time: 5:26 PM
 */
public class ApplicationState {

    public static CanvasMode canvasMode;
    public static Paint paint;
    public static FileMode fileMode;
    public static String originalFileName;
    public static Bitmap imageToShare;

    public enum FileMode {
        NEW,
        LOAD
    }

    public enum CanvasMode {
        DRAWING,
        SCROLLING,
        ERASING
    }
}
