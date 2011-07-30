package com.jia.tabpad.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.jia.tabpad.R;
import com.jia.tabpad.main.ApplicationState;
import com.jia.tabpad.main.Config;

import java.io.ByteArrayOutputStream;

/**
 * Authorize and Share on Facebook
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 7/2/11
 * Time: 11:22 PM
 */
public class ShareOnFacebook extends Activity implements View.OnClickListener {

    Facebook facebook = new Facebook(Config.FACEBOOK_APP_ID);
    private AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_dialog);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ShareOnFacebook.this);
        String access_token = prefs.getString("access_token", null);
        Long expires = prefs.getLong("access_expires", -1);


        if (access_token != null && expires != -1) {
            facebook.setAccessToken(access_token);
            facebook.setAccessExpires(expires);
            shareImage();
        }


        if (!facebook.isSessionValid()) {
            facebook.authorize(this, new String[] { "publish_stream", "user_photos" }, new Facebook.DialogListener() {
                @Override
                public void onComplete(Bundle values) {
                    String token = facebook.getAccessToken();
                    long token_expires = facebook.getAccessExpires();

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ShareOnFacebook.this);

                    prefs.edit().putLong("access_expires", token_expires).commit();

                    prefs.edit().putString("access_token", token).commit();

                    shareImage();
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
    }

    
    public void shareImage() {
        byte[] data = null;

        Bitmap bi = ApplicationState.imageToShare;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bi.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        data = baos.toByteArray();

        Bundle params = new Bundle();
        params.putString("message", "Checkout this TabPad shoutout!");
        params.putByteArray("picture", data);

        AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
        mAsyncRunner.request("me/photos", params, "POST", new FacebookIdRequestListener(), null);

        finish();
    }

    /**
     * This method will create a new album on facebook
     */
    private void createNewAlbum(String albumName, String albumDescription) {
        Bundle params = new Bundle();
        //params.putString("method", "photos.upload");
        params.putString("name", albumName);
        params.putString("message", albumDescription);
        AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
        mAsyncRunner.request("me/albums", params, "POST", new FacebookIdRequestListener(), null);
        finish();
    }

    /**
     * This posts a message to the wall
     * @param msg
     */
    private void postOnWall(String msg) {
        Bundle params = new Bundle();
	    params.putString("message", msg);
        mAsyncRunner.request("me/feed", params, "POST", new FacebookIdRequestListener(), null);
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