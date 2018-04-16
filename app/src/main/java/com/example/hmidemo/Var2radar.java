package com.example.hmidemo;

import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/3/4 0004.
 */

public class Var2radar {
    private BroadListener broadListener;
    private  int var;
    private static Var2radar varInstance;

    public static Var2radar getInstance(){
        if(null==varInstance)
        {
            varInstance=new Var2radar();
        }
        return varInstance;
    }

    public interface BroadListener{
        View onSuccess();
//        void onFail(String string);
    }
    public void setBroadListener(BroadListener broadListener){
        this.broadListener=broadListener;
        Log.i(TAG,"setBroadListener");
    }

    public void info(){
        if (broadListener!=null){
            broadListener.onSuccess();
        }else{
            Log.i("123","UI更新出错");
        }

    }

}
