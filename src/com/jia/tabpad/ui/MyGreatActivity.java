package com.jia.tabpad.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.jia.tabpad.R;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 7/2/11
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyGreatActivity extends Activity implements View.OnClickListener {

    Facebook facebook = new Facebook("216482771730434");
    private AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);

    //"216482771730434"
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyGreatActivity.this);
        String access_token = prefs.getString("access_token", null);
        Long expires = prefs.getLong("access_expires", -1);


        if (access_token != null && expires != -1) {
            facebook.setAccessToken(access_token);
            facebook.setAccessExpires(expires);
        }


        if (!facebook.isSessionValid()) {
            facebook.authorize(this, new Facebook.DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                    String token = facebook.getAccessToken();
                    long token_expires = facebook.getAccessExpires();

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyGreatActivity.this);

                    prefs.edit().putLong("access_expires", token_expires).commit();

                    prefs.edit().putString("access_token", token).commit();


                }

                @Override
                public void onFacebookError(FacebookError error) {
                }

                @Override
                public void onError(DialogError e) {
                }

                @Override
                public void onCancel() {
                }
            });
        }


        Button b = (Button) findViewById(R.id.buttonLogin);
        b.setOnClickListener(this);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        try {
            if (facebook.isSessionValid()) {
                mAsyncRunner.request("me", new FacebookIdRequestListener());
            }

        } catch (Exception e) {
            System.out.println("error");
        }

    }
}