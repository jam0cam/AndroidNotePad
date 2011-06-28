package com.jia.tabpad.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.jia.tabpad.main.ApplicationState;
import com.jia.tabpad.main.Config;
import com.jia.tabpad.R;

import java.io.*;

public class DrawActivity extends Activity implements ColorPickerDialog.OnColorChangedListener, StrokeWidthPickerDialog.OnStrokeSelectListener {

    private String fileName;
    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set full screen view
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        //this initializes with the default paint values
        if (savedInstanceState == null)
            initApplicationState();

        Bundle b = getIntent().getExtras();

        if (b != null) {
            fileName = b.getString("loadFile");
            try {
                loadFile(fileName);
            } catch (Exception e) {
            }
        } else {
            newFile();
        }

        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);

    }

    private void initApplicationState() {
        Paint paint = new Paint();
        paint.setColor(Config.DEFAULT_FORE_COLOR);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(Config.DEFAULT_STROKE_WIDTH);
        paint.setStrokeCap(Paint.Cap.ROUND);

        ApplicationState.canvasMode = ApplicationState.CanvasMode.DRAWING;
        ApplicationState.paint = paint;
    }

    public boolean validateTitle() {
        EditText txt = (EditText) findViewById(R.id.txtFileTitle);
        String currentFileName = txt.getText().toString();

        //failed, because no title was given
        if (currentFileName.equals(this.getString(R.string.untitle)))
            return false;

        //This is the name that it started with
        if (currentFileName.equalsIgnoreCase(ApplicationState.originalFileName))
            return true;

        //failed because file name is already used
        if (isFileNameUsed(currentFileName))
            return false;

        return true;
    }

    public void showDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(text);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void saveFile() throws IOException {
        if (!validateTitle()) {
            showDialog("Please give your file a new title.");
            ((EditText) findViewById(R.id.txtFileTitle)).requestFocus();
            return;
        }

        fileName = ((EditText) findViewById(R.id.txtFileTitle)).getText().toString();
        if (!ApplicationState.originalFileName.equalsIgnoreCase(this.getString(R.string.untitle)) &&
                !fileName.equalsIgnoreCase(ApplicationState.originalFileName)) {
            //this means the file was loaded and the file name was changed. Delete the original file
            deleteFile(ApplicationState.originalFileName);
        }
        FileOutputStream fos = this.openFileOutput(fileName, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linLayout);
        DrawView view = (DrawView) layout.getChildAt(0);

        view.writeObject(os);
        os.close();

        Context context = getApplicationContext();
        CharSequence text = fileName + " saved.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

    }

    public void loadFile(String fileName) throws IOException, ClassNotFoundException {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linLayout);
        layout.removeAllViews();

        FileInputStream fis = this.openFileInput(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        DrawView drawView = new DrawView(this, ApplicationState.paint);
        drawView.readObject(is);
        is.close();
        drawView.setLayoutParams(new ViewGroup.LayoutParams(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT));
        layout.addView(drawView);
        ApplicationState.fileMode = ApplicationState.FileMode.LOAD;
        ApplicationState.originalFileName = fileName;
        updateActionBar();
    }

    public void newFile() {
        DrawView view = new DrawView(this, ApplicationState.paint);
        view.setLayoutParams(new ViewGroup.LayoutParams(Config.CANVAS_WIDTH, Config.CANVAS_HEIGHT));
        LinearLayout layout = (LinearLayout) findViewById(R.id.linLayout);
        layout.removeAllViews();

        layout.addView(view);
        fileName = getString(R.string.untitle);

        EditText txt = (EditText) findViewById(R.id.txtFileTitle);
        if (txt != null)
            txt.setText(fileName);
        ApplicationState.fileMode = ApplicationState.FileMode.NEW;
        ApplicationState.originalFileName = fileName;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        EditText title = (EditText) menu.findItem(R.id.pageTitle).getActionView();
        title.setText(fileName);
        this.menu = menu;
        updateActionBar();
        return true;
    }


    private void showLoadFileActivity() {
        Intent myIntent = new Intent(this, OpenFileActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void undo() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linLayout);
        DrawView view = (DrawView) layout.getChildAt(0);
        view.undoLastAction();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        try {
            switch (item.getItemId()) {
                case R.id.new_file:
                    newFile();
                    break;
                case R.id.save_file:
                    saveFile();
                    break;
                case R.id.open_file:
                    showLoadFileActivity();
                    break;
                case R.id.undo:
                    undo();
                    break;
                case R.id.colorPicker:
                    ColorPickerDialog picker = new ColorPickerDialog(this, this, ApplicationState.paint.getColor());
                    picker.show();
                    ApplicationState.canvasMode = ApplicationState.CanvasMode.DRAWING;
                    break;
                case R.id.strokePicker:
                    StrokeWidthPickerDialog pickerDialog2 = new StrokeWidthPickerDialog(this, this, Math.round(ApplicationState.paint.getStrokeWidth()));
                    pickerDialog2.show();
                    ApplicationState.canvasMode = ApplicationState.CanvasMode.DRAWING;
                    break;
                case R.id.erase:
                    ApplicationState.canvasMode = ApplicationState.CanvasMode.ERASING;
                    break;
                case R.id.brush:
                    ApplicationState.canvasMode = ApplicationState.CanvasMode.DRAWING;
                    break;
                case R.id.scroller:
                    ApplicationState.canvasMode = ApplicationState.CanvasMode.SCROLLING;
                    break;
                case R.id.exit:
                    Config.IS_APP_QUITTING = true;
                    finish();
                    break;
                case R.id.about:
                    AboutDialog ad = new AboutDialog(this);
                    ad.show();
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }

        updateActionBar();
        return super.onOptionsItemSelected(item);
    }


    private boolean isFileNameUsed(String txt) {
        String files[] = fileList();

        for (String s : files) {
            if (s.equalsIgnoreCase(txt))
                return true;
        }

        return false;
    }

    private void updateActionBar() {
        if (ApplicationState.canvasMode == ApplicationState.CanvasMode.DRAWING) {
            menu.findItem(R.id.brush).setIcon(R.drawable.brush_selected_32);
            menu.findItem(R.id.scroller).setIcon(R.drawable.scroll_32);
            menu.findItem(R.id.erase).setIcon(R.drawable.eraser_32);
        } else if (ApplicationState.canvasMode == ApplicationState.CanvasMode.SCROLLING) {
            menu.findItem(R.id.brush).setIcon(R.drawable.brush_32);
            menu.findItem(R.id.scroller).setIcon(R.drawable.scroll_selected_32);
            menu.findItem(R.id.erase).setIcon(R.drawable.eraser_32);
        } else if (ApplicationState.canvasMode == ApplicationState.CanvasMode.ERASING) {
            menu.findItem(R.id.brush).setIcon(R.drawable.brush_32);
            menu.findItem(R.id.scroller).setIcon(R.drawable.scroll_32);
            menu.findItem(R.id.erase).setIcon(R.drawable.eraser_selected_32);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Config.IS_APP_QUITTING)
            System.exit(1);
    }

    @Override
    public void colorChanged(int color) {
        ApplicationState.paint.setColor(color);
    }

    @Override
    public void strokeSelected(int strokeWidth) {
        ApplicationState.paint.setStrokeWidth(strokeWidth);
    }
}