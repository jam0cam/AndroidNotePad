package com.jia.tabpad.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.jia.tabpad.R;
import com.jia.tabpad.main.ApplicationState;
import com.jia.tabpad.main.Config;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 7/2/11
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShareOnFacebook extends Activity implements View.OnClickListener {

    Facebook facebook = new Facebook(Config.FACEBOOK_APP_ID);
    private AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);

    //"216482771730434"
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ShareOnFacebook.this);
        String access_token = prefs.getString("access_token", null);
        Long expires = prefs.getLong("access_expires", -1);


        if (access_token != null && expires != -1) {
            facebook.setAccessToken(access_token);
            facebook.setAccessExpires(expires);
        }


        if (!facebook.isSessionValid()) {
            facebook.authorize(this, new String[] { "publish_stream" }, new Facebook.DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                    String token = facebook.getAccessToken();
                    long token_expires = facebook.getAccessExpires();

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ShareOnFacebook.this);

                    prefs.edit().putLong("access_expires", token_expires).commit();

                    prefs.edit().putString("access_token", token).commit();

                    share();
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
        } else {
            share();
        }
    }

    
    public void share() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bm = ApplicationState.imageToShare;
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        Bundle param = new Bundle();
        param.putString("message", "picture caption");
        param.putByteArray("picture", b);
        mAsyncRunner.request("me/photos", param, new FacebookIdRequestListener(), "POST");

        finish();
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