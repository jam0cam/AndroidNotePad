package com.jia.tabpad;

import android.graphics.Paint;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/23/11
 * Time: 5:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationState {

    public static CanvasMode canvasMode;
    public static Paint paint;
    public static FileMode fileMode;
    public static String originalFileName;

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
