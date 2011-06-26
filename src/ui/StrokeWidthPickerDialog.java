package ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.Config;

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

    private Context parent;
    public int currentProgress;
    OnStrokeSelectListener mListener;
    private SeekBar seekBar;
    private TextView tv;


    public StrokeWidthPickerDialog(Context context,
                                   OnStrokeSelectListener listener,
                                   int initialStroke
                                   ) {
        super(context);
        this.parent = context;
        mListener = listener;
        currentProgress = initialStroke;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this.parent);
        layout.setOrientation(LinearLayout.VERTICAL);
        //layout.setLayoutParams(new LinearLayout.LayoutParams(Config.STROKE_PICKER_WIDTH, Config.STROKE_PICKER_HEIGHT));

        seekBar = getSeekBar();
        seekBar.setOnSeekBarChangeListener(this);
        tv = getTextView();

        layout.addView(tv);
        layout.addView(seekBar);

        setContentView(layout);

        setTitle("Pick a Stroke Width");

    }

    private SeekBar getSeekBar() {
        SeekBar bar = new SeekBar(this.parent);
        LinearLayout.LayoutParams vg = new LinearLayout.LayoutParams(Config.SEEKBAR_WIDTH, Config.SEEKBAR_HEIGHT);
        vg.gravity= Gravity.CENTER_HORIZONTAL;
        bar.setLayoutParams(vg);
        bar.setProgress(currentProgress);
        bar.setMax(50);
        bar.setPadding(20,20,20,20);
        return bar;
    }

    private TextView getTextView () {
        TextView tv =new TextView(this.parent);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Config.SEEKBAR_TEXT_WIDTH, Config.SEEKBAR_TEXT_HEIGHT);
        params.gravity=Gravity.CENTER_HORIZONTAL;
        tv.setGravity(Gravity.BOTTOM);
        tv.setLayoutParams(params);
        tv.setText(String.valueOf(currentProgress));
        tv.setTextSize(25);
        return tv;
    }


    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        tv.setText(String.valueOf(progress));
        currentProgress = progress;
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        System.out.println("hellow");
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        System.out.println("hellow");
    }

    @Override
    public void dismiss() {
        mListener.strokeSelected(currentProgress);
        super.dismiss();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
