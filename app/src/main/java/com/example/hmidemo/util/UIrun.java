package com.example.hmidemo.util;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/12/15 0015.
 */

public class UIrun {
    public static void SetText(final TextView name, final Object object){
        name.post(new Runnable() {
            @Override
            public void run() {
                name.setText(object.toString());
            }
        });
    }
    public static void SetImage(final ImageView name, final Object object){
        name.post(new Runnable() {
            @Override
            public void run() {
                name.setImageResource((Integer) object);
            }
        });

    }
}
