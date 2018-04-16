package com.example.hmidemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context,"Have new message",Toast.LENGTH_SHORT).show();
        Var.getInstance().setVar(1);
        Var2radar.getInstance().info();
        Var3bar.getInstance().setVar(1);
        Var4line.getInstance().setVar(1);

    }
}
