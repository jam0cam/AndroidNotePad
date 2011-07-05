package com.jia.tabpad.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.jia.tabpad.R;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 7/4/11
 * Time: 7:04 PM
 */
public class ShareDialog extends Dialog implements View.OnClickListener{
    private Context context;

    public ShareDialog(Context c) {
        super(c);
        this.context = c;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Share On");
        setContentView(R.layout.share_list_item);

        LinearLayout layout = (LinearLayout)findViewById(R.id.facebookItem);
        layout.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.facebookItem) {
            System.out.println("Share on facebook");
        }

        dismiss();
    }
}
