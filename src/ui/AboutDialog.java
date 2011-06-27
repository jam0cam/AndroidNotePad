package ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * TODO: JIA: Comment this
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/26/11
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class AboutDialog extends Dialog {
    Context context;

    public AboutDialog(Context c) {
        super (c);
        this.context = c;
    }

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this.context);
        layout.setOrientation(LinearLayout.VERTICAL);


    }


}
