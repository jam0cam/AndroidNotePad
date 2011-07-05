package com.jia.tabpad.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jia.tabpad.R;

/**
 * This is an adapter class for a listview that contains a textview and an imageview.
 * 
 * Created by IntelliJ IDEA.
 * User: jitse
 * Date: 6/19/11
 * Time: 8:47 PM
 */
public class FileItemAdaper extends ArrayAdapter<String> {
    private String items[];     //contains the file names to be loaded
    private Context context;
    private View.OnClickListener clickListener;

    public FileItemAdaper(Context context, int textViewResourceId, String items[], View.OnClickListener clickListener) {
        super(context, textViewResourceId, items);
        this.items = items;
        this.context = context;
        this.clickListener = clickListener;
    }

    /**
     * For each position passed in, return the view with the filename of that position and a delete
     * image as the imageview
     * 
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.file_list_item, null);
        }
        String s = items[position];

        TextView tt = (TextView) v.findViewById(R.id.txtFileName);
        if (tt != null) {
            tt.setText(s);
            tt.setOnClickListener(clickListener);
        }

        ImageView iv = (ImageView)v.findViewById(R.id.imgFileDelete);
        iv.setImageResource(R.drawable.close_32);
        iv.setOnClickListener(clickListener);
        return v;
    }
}
