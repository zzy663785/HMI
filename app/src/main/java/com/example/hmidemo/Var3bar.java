package com.example.hmidemo;

import android.util.Log;
import android.view.View;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/3/4 0004.
 */

public class Var3bar {
    private BroadListener broadListener;
    private  int var;
    private static Var3bar varInstance;
    private Var3bar(){

    }
    public static Var3bar getInstance(){
        if(null==varInstance)
        {
            varInstance=new Var3bar();
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
    /**
     * @return the var
     */
    public int getVar() {
        return var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(int var) {
        this.var = var;
        if(null!=broadListener){
            if(var==1){
                broadListener.onSuccess();
            }
//            else {
//                broadListener.onFail("收到消息更新失败");
//            }
        }

    }
}
