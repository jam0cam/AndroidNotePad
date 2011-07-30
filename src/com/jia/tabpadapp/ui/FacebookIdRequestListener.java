package com.jia.tabpadapp.ui;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.FacebookError;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * The Listener for after sharing on facebook
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 7/3/11
 * Time: 3:11 PM
 */
public class FacebookIdRequestListener implements AsyncFacebookRunner.RequestListener {
    @Override
    public void onComplete(String response, Object state) {
        System.out.println("onComplete");
        System.out.println(response.toString());
    }

    @Override
    public void onIOException(IOException e, Object state) {
        System.out.println("onException");
        System.out.println(e.getMessage());
    }

    @Override
    public void onFileNotFoundException(FileNotFoundException e, Object state) {
        System.out.println("onException");
        System.out.println(e.getMessage());
    }

    @Override
    public void onMalformedURLException(MalformedURLException e, Object state) {
        System.out.println("onException");
        System.out.println(e.getMessage().toString());
    }

    @Override
    public void onFacebookError(FacebookError e, Object state) {
        System.out.println("onError");
        System.out.println(e.getMessage());
    }
}
