package com.jia.tabpad.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.jia.tabpad.R;

/**
 * This is the About Dialog.  Displays information about the current app and author information
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/26/11
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class AboutDialog extends AlertDialog {
    Context context;

    public AboutDialog(Context c) {
        super(c);
        this.context = c;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.app_name);
        setContentView(R.layout.about);
        TextView tv = (TextView)findViewById(R.id.txtAbout);
        tv.setTextSize(25);

        StringBuilder sb = new StringBuilder();
        sb.append("TabPad is an app designed for tablet use only.\n");
        sb.append("Author: Jia Tse\n");
        sb.append("Email: jiajtse@gmail.com\n\n");
        sb.append("Features:\n");
        sb.append("--Take notes on an extended canvas\n");
        sb.append("--Pan top/down, left/right for more note taking space\n");
        sb.append("--Choose between different colors and stroke sizes\n");
        sb.append("--Erase and undo capabilities\n");
        sb.append("--Create new, save and open existing notes\n\n");
        sb.append("Feel free to email your features requests.  Thank you for your support.\n");

        tv.setText(sb.toString());
    }


}
