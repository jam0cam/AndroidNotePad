package com.jia.tabpad.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.jia.tabpad.main.FileItemAdaper;
import com.jia.tabpad.R;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/18/11
 * Time: 7:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenFileActivity extends ListActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set full screen view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.list_activity);

        load();
    }

    private void load() {

        ListView lv = getListView();
        // lv.removeAllViews();
        FileItemAdaper adapter = new FileItemAdaper(this, R.layout.list_item, fileList(), this);
        lv.setAdapter(adapter);
    }

    private void loadFile(String fileName) {
        Bundle b = new Bundle();

        b.putString("loadFile", fileName);

        Intent myIntent = new Intent(this, DrawActivity.class);
        myIntent.putExtras(b);
        startActivity(myIntent);
        finish();
    }


    @Override
    public void onClick(View view) {
        if (view.getClass().getSimpleName().equals("TextView")) {
            TextView tv = ((TextView) view);
            loadFile(tv.getText().toString());
        } else if (view.getClass().getSimpleName().equals("ImageView")) {
            LinearLayout layout = (LinearLayout) view.getParent();
            TextView tv = (TextView) layout.getChildAt(0);
            this.deleteFile(tv.getText().toString());
            load();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent myIntent = new Intent(this, DrawActivity.class);
            startActivity(myIntent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
