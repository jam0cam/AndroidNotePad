package ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.R;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/19/11
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    SeekBar mSeekBar;
    TextView mProgressText;
    String titleText = "Stroke Width: ";
    int currentProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test);

        mSeekBar = (SeekBar) findViewById(R.id.seekbar1);
        mSeekBar.setOnSeekBarChangeListener(this);
        mProgressText = (TextView) findViewById(R.id.txtseekBarTitle);


    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        mProgressText.setText(titleText + String.valueOf(progress));
        currentProgress = progress;
    }

    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}
