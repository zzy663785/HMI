package com.example.hmidemo.util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.Socket;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class Connection {

    public static  boolean CONNECTION_STATE;
//    public static CountDownLatch cdl=new CountDownLatch(1);//这里的数字，开启几个线程就写几

    public Socket socket = null;
    public InputStream is = null;

    public InputStreamReader isr =null;
    public BufferedReader br = null;
    private InputStream inputStream;
    private OutputStream outputStream;
    public DataInputStream DataInput;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    public static StringBuilder result;
    public static int[] intarr;


    public  void getConnect(final String IP, final int PORT, final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (socket == null) {
//                        String IPs = "192.168.43.84";
//                        int PORTs =8080;
                        socket = new Socket(IP, PORT);
                        if (socket.isConnected()) {
                            CONNECTION_STATE = true;
                            Log.i(TAG,"CONNECTION  SOCKET"+"  "+CONNECTION_STATE);
                            while (true){
                                getMessage(context);
                            }
                        } else {
                            CONNECTION_STATE = false;
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
//        cdl.countDown();                             Log.i(TAG,"CONNECTION  SOCKET!!!   2  "+cdl);
    }

//    public  void getMessage(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true) {
//                        Log.i(TAG, "get message………………");
//                            DataInput=new DataInputStream(socket.getInputStream());
//                            String result=DataInput.readUTF().toString();
//                            Log.i(TAG,"Receive……"+result);
//                        }
////                        BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
////                        String info=null;
////                        while ((info=br.readLine())!=null){
////                            Log.i(TAG,"Receive……"+info);
////                        }
//                    }
//                 catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

//    public class MyService extends Service{
//
//        @Nullable
//        @Override
//        public IBinder onBind(Intent intent) {
//            return null;
//        }
//        @Override
//        public void onCreate() {
//            Log.i(TAG,"111111");
//            super.onCreate();
//        }
//        @Override
//        public int onStartCommand(Intent intent, int flags, int startId) {
//            Log.i(TAG,"555555");
//            new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    inputStream=socket.getInputStream();
//                    buffer=new byte[20];
//                    inputStream.read(buffer);
//                    for (int i=0;i<buffer.length;i++){
//                      for (int j=0;j<buffer.length;j++){
//                          Data=new int[20];
//                         Data[j]=buffer[i]&0xff;
//                      }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//            return super.onStartCommand(intent, flags, startId);
//        }
//
//        @Override
//        public void onDestroy() {
//            super.onDestroy();
//        }
//
//    }

    public  void getMessage(Context context) throws IOException {

        Log.i(TAG, "get message………………");
        Reader reader = new InputStreamReader(socket.getInputStream());
        char chars[] = new char[40];
        int len;
        result = new StringBuilder();
        String temp;
        int index;
        while ((len=reader.read(chars)) != -1) {
            temp = new String(chars, 0, len);
            if ((index = temp.indexOf("end")) != -1) {//遇到e时就结束接收
                result.append(temp.substring(0, index));
                break;
            }
            result.append(temp);
        }
        Log.i(TAG,"……from client:  " + result);
/*
字符串数据转换为int数组形式
 */
        String[] arrss=new String[40];
        String ss=result.toString();
        arrss=ss.split(" ");
        intarr=new int[arrss.length];
        for (int i=0;i<arrss.length;i++){
            intarr[i]=Integer.parseInt(arrss[i]);
            Log.i(TAG,intarr[i]+"   Int类型结果");
        }
        Intent intent=new Intent("HelloNewMessage");
        context.sendBroadcast(intent);

    }

    public  void sendMessage(final String sendData){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    outputStream=socket.getOutputStream();
                    //数据的结尾加上换行符才可让服务器端的readline()停止阻塞
                    outputStream.write(sendData.getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void disconnect(){
        try {
            outputStream.close();
            bufferedReader.close();
            socket.close();
            if(socket.isConnected()){
                CONNECTION_STATE=false;
                Log.i(TAG,"断开成功！");
            }else {
                CONNECTION_STATE=true;
                Log.i(TAG,"断开失败！");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
