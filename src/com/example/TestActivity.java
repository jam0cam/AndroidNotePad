package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/19/11
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestActivity   extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set full screen view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.test);
    }
}
