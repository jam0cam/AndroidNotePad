package com.jia.tabpadapp.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.jia.tabpadapp.main.FileItemAdaper;
import com.jia.tabpadapp.R;

/**
 * An activity that allows the user to choose a file to open.
 *
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/18/11
 * Time: 7:33 PM
 */
public class OpenFileActivity extends ListActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set full screen view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.file_list_activity);

        load();
    }

    /**
     * Gets the listview and sets it with the FileItemAdapter
     */
    private void load() {
        ListView lv = getListView();
        FileItemAdaper adapter = new FileItemAdaper(this, R.layout.file_list_item, fileList(), this);
        lv.setAdapter(adapter);
    }

    /**
     * Calls DrawActivity to load the specified file
     * @param fileName
     */
    private void loadFile(String fileName) {
        Bundle b = new Bundle();

        b.putString("loadFile", fileName);

        Intent myIntent = new Intent(this, DrawActivity.class);
        myIntent.putExtras(b);
        startActivity(myIntent);
        finish();
    }


    /**
     * A view was clicked.  If it's the TextView, then the user wants to load this file.  So load it.
     * If it's the ImageView (delete image), then the user wants to delete this file, so delete it and
     * reload this page.
     * @param view
     */
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

    /**
     * If the user clicks back, then go back to the canvas and start a new page
     * @param keyCode
     * @param event
     * @return
     */
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
