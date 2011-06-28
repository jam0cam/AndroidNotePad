package com.jia.tabpad.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import com.jia.tabpad.R;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/25/11
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class StrokeWidthPickerDialog extends Dialog implements SeekBar.OnSeekBarChangeListener {

    public interface OnStrokeSelectListener {
        void strokeSelected(int strokeWidth);
    }

    public int currentProgress;
    OnStrokeSelectListener mListener;
    private SeekBar seekBar;
    private TextView tv;


    public StrokeWidthPickerDialog(Context context,
                                   OnStrokeSelectListener listener,
                                   int initialStroke
                                   ) {
        super(context);
        mListener = listener;
        currentProgress = initialStroke;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Pick a Stroke Width");
        setContentView(R.layout.stroke_width_picker_dialog);

        tv = (TextView)findViewById(R.id.txtSeekBarTitle);
        tv.setText(String.valueOf(currentProgress));
        seekBar = (SeekBar)findViewById(R.id.seekbar1);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setProgress(currentProgress);
    }


    

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        tv.setText(String.valueOf(progress));
        currentProgress = progress;
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        String s;
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
                String s;
    }

    @Override
    public void dismiss() {
        mListener.strokeSelected(currentProgress);
        super.dismiss();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
