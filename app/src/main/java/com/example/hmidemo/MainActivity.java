package com.example.hmidemo;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.hmidemo.gson.Weather;
import com.example.hmidemo.util.Connection;
import com.example.hmidemo.util.HttpUtil;
import com.example.hmidemo.util.UIrun;
import com.example.hmidemo.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import fragment.Downfragment1;
import fragment.UpLeftfragment;
import fragment.UpLeftfragment2;
import fragment.UpRightfragment;
/**
 * Created by zzy on 2017/12/9
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "hmidemo";
    private Chronometer showTimer;
    private EditText Dialog_IP;
    private EditText Dialog_PORT;
    private EditText Edit_IP;
    private EditText Edit_Port;
    private AlertDialog dialog;
    String IP,Port;
    Connection conn;
    private TextView degreeText;
    private TextView weatherInfoText;
    private TextView CityText;

    String cityname;
    String temperature;
    String cond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

//    public MainActivity() {
//        mainActivity = this;
//    }
//    public static MainActivity getMainActivity() {
//        return mainActivity;
//    }
//
//    private static MainActivity mainActivity;

    void init(){

        replacefragment(new UpRightfragment(),R.id.upsideR,false);

        replacefragment(new UpLeftfragment(),R.id.upsideL,false);
        replacefragment(new Downfragment1(),R.id.downside,false);

        Edit_IP= (EditText) findViewById(R.id.edit_IP);
        Edit_Port= (EditText) findViewById(R.id.edit_PORT);
        Button btHome = (Button) findViewById(R.id.bt_home);
        Button btSetting = (Button) findViewById(R.id.bt_setting);
        Button btPlatoon = (Button) findViewById(R.id.bt_platoon);
        Button btCar = (Button) findViewById(R.id.bt_car);
        Button btLink = (Button) findViewById(R.id.bt_link);
        Button btLocation = (Button) findViewById(R.id.bt_location);
//        DialogBtConfir=findViewById(R.id.bt_confirm);
        showTimer= (Chronometer) findViewById(R.id.Timer);

        degreeText=findViewById(R.id.degree_text);
//        DirText=findViewById(R.id.weather_wind_dir);
//        ScText=findViewById(R.id.weather_wind_sc);
//        apiText=findViewById(R.id.api_text);
//        pm25Text=findViewById(R.id.pm25_text);
        weatherInfoText=findViewById(R.id.weather_info_text);
        CityText=findViewById(R.id.cityname);

        btHome.setOnClickListener(this);
        btPlatoon.setOnClickListener(this);
        btSetting.setOnClickListener(this);
        btCar.setOnClickListener(this);
        btLink.setOnClickListener(this);
        btLocation.setOnClickListener(this);
//        DialogBtConfir.setOnClickListener(this);

//        String weatherId ="CN101040100";//重庆 CN101040100；；桐乡CN101210304
//        requestWeather(weatherId);
//        requestWeather= new RequestWeather(MainActivity.this);
//        requestWeather.weatherLocation();
        weatherLocation();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_home:
                replacefragment(new UpLeftfragment(),R.id.upsideL,false);
                break;
            case R.id.bt_setting:
                showDialog();
                break;
            case R.id.bt_platoon:
                showPopupWindow();
                break;
            case R.id.bt_car:
                replacefragment(new UpLeftfragment2(),R.id.upsideL,false);
                break;
            case R.id.bt_location:
                Intent intent=new Intent(MainActivity.this,MapActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_link:
                if (TextUtils.isEmpty(Edit_Port.getText())&&TextUtils.isEmpty(Edit_IP.getText())){
                    Toast.makeText(this,"请输入IP地址与端口号！",Toast.LENGTH_SHORT).show();
                }else {
                    int PORT= Integer.valueOf(Edit_Port.getText().toString());
                    String IP=Edit_IP.getText().toString();                 Log.i(TAG,IP+"  "+PORT);
                    conn = new Connection();
                    conn.getConnect(IP,PORT,getApplicationContext());
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Connection.CONNECTION_STATE){
                    Log.i(TAG,Connection.CONNECTION_STATE+"!!!!");
                    Toast.makeText(getApplicationContext(),"连接成功!",Toast.LENGTH_SHORT).show();
                    showTime();
                }else {
                    Log.i(TAG,Connection.CONNECTION_STATE+"!!!!");
                    Toast.makeText(getApplicationContext(),"连接失败！",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.pop_departure:
                conn.sendMessage("哈哈哈哈!");
                break;
            case R.id.pop_join:
                conn.sendMessage("啊呀!");
                break;
            default:break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Connection.CONNECTION_STATE){
        conn.disconnect();
        }
    }

    public void replacefragment(Fragment fragment, int location, boolean add){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        if (location==R.id.upsideR){
            transaction.replace(R.id.upsideR, fragment);
            if (add){
            transaction.addToBackStack(null);
            }
        }
        if (location==R.id.upsideL){
            transaction.replace(R.id.upsideL, fragment);
            if (add){
                transaction.addToBackStack(null);
            }
        }
        if (location==R.id.downside){
            transaction.replace(R.id.downside, fragment);
            if (add){
                transaction.addToBackStack(null);
            }
        }
        transaction.commit();
    }


    //声明定位回调监听器
    private AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    Double Latitude= amapLocation.getLatitude();//纬度
                    Double Longitude=amapLocation.getLongitude();
//                    LocationId.append(amapLocation.getLatitude()).append(":").append(amapLocation.getLongitude());
                    Log.i("123",Latitude+"~~~~"+Longitude);
                    requestWeather(Latitude,Longitude);
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };


    void weatherLocation(){
        Log.i("123","weatherlocation");
        AMapLocationClient mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(mLocationListener);//定位监听回调

        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);//低功耗定位模式
        mLocationOption.setOnceLocation(true);//定位一次
        mLocationOption.setOnceLocationLatest(false);
        mLocationOption.setLocationCacheEnable(false);

        mLocationClient.setLocationOption(mLocationOption);//给定客户端对象设置定位的参数
        mLocationClient.startLocation();
        Log.i("123","weatherlocation end");

    }
    /*
    public void requestWeather(final String weatherId)
     */
    public void requestWeather(Double Latitude,Double Longitude) {
//        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=18716bf28c8b43a08d2e75f10f937bbd";
        String weatherUrl="https://api.seniverse.com/v3/weather/now.json?key=w6xrwvyx75iq9jqb&location="+Latitude+":"+Longitude+"&language=zh-Hans&unit=c";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(),"获取天气信息失败！",Toast.LENGTH_SHORT).show();
                Log.i(TAG, "获取天气信息失败！");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Weather weather = Utility.handlerWeatherResponse(responseText);//Weather weather = Utility.handlerWeatherResponse(responseText);
                Log.i(TAG, responseText);

                if (weather != null ) {
                    try {
                         cityname=weather.location.cityname;
                         temperature=weather.now.temperature+"℃";
                         cond=weather.now.cond;
                        Log.i("123",cityname+temperature+cond);
//                        if (weather.now.sc=="0"){
//                            Sc="静风";
//                        }else {
//                            Sc=weather.now.sc+"级";
//                        }
//
//                        pm25Info=weather.aqi.city.pm25;
//                        CityInfo=weather.basic.cityName;                        Log.i(TAG,CityInfo);
                    }catch (Exception e){
                        Log.i("weather","天气数据异常");
                    }

//                    UIrun.SetText(DirText,Dir);
//                    UIrun.SetText(ScText,Sc);
                    UIrun.SetText(degreeText,temperature);
                    UIrun.SetText(weatherInfoText,cond);
//                    UIrun.SetText(apiText,apiInfo);
//                    UIrun.SetText(pm25Text,pm25Info);
                    UIrun.SetText(CityText,cityname);

//                    if (weatherInfo.equals("阴")){
//                        UIrun.SetImage(imageView,R.drawable.yin);
//                    }else if (weatherInfo.equals("晴")){
//                        UIrun.SetImage(imageView,R.drawable.qing);
//                    }else if (weatherInfo.equals("多云")){
//                        UIrun.SetImage(imageView,R.drawable.duoyun);
//                    }else if (weatherInfo.contains("雨")){
//                        UIrun.SetImage(imageView,R.drawable.zhongyu);
//                    }
                } else {
                    Toast.makeText(getApplicationContext(),"获取天气信息失败！",Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "获取天气信息失败！");
                }
            }
        });
    }

    /**
     * 计时部分
     */
    private void showTime(){
        showTimer.setBase(SystemClock.elapsedRealtime());
        int hour= (int) ((SystemClock.elapsedRealtime()-showTimer.getBase())/1000/60);
        showTimer.setFormat("0"+String.valueOf(hour)+":%s");
        showTimer.start();
        TextView state= (TextView) findViewById(R.id.consho);//修改连接状态
        state.setText("已连接至");
    }

    /**
     * 弹出设置对话框
     */
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(null);
        builder.setTitle("设 置");

        LinearLayout Dialog= (LinearLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_dialog_setting,null);
        builder.setView(Dialog);
        Dialog_IP=Dialog.findViewById(R.id.Dialog_inputIP);
        Dialog_PORT=Dialog.findViewById(R.id.Dialog_inputPORT);

//        builder.setPositiveButton(R.string.postive_button, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"点击了确认按钮！",Toast.LENGTH_SHORT).show();
//            }
//        });
        builder.setCancelable(true);
        dialog= builder.create();
        dialog.show();

        Dialog.findViewById(R.id.bt_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Port= Dialog_PORT.getText().toString();
                IP =Dialog_IP.getText().toString();
                Edit_Port.setText(Port);
                Edit_IP.setText(IP);
                dialog.dismiss();

            }
        });
        Dialog.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }


    private void showPopupWindow(){
        View contentView= LayoutInflater.from(MainActivity.this).inflate(R.layout.popupwindow,null);
        //，如果focusable为false，在一个Activity弹出一个PopupWindow，按返回键，由于PopupWindow没有焦点，会直接退出Activity。
        // 如果focusable为true，PopupWindow弹出后，所有的触屏和物理按键都有PopupWindows处理。
        PopupWindow mPopWindow = new PopupWindow(contentView, 600, 400, true);
        TextView Join=contentView.findViewById(R.id.pop_join);
        TextView Depart=contentView.findViewById(R.id.pop_departure);
        Join.setOnClickListener(this);
        Depart.setOnClickListener(this);
        LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main,null);
       // View rootview=LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_main,null);

//        mPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
//        mPopWindow.setAnimationStyle(R.drawable.popupwindow_bg);

        mPopWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    /*
        showAsDropDown(View anchor)：相对某个控件的位置（正左下方），无偏移
        showAsDropDown(View anchor, int xoff, int yoff)：相对某个控件的位置，有
        偏移
        showAtLocation(View parent, int gravity, int x, int y)：
        相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），
        可以设置偏移或无偏移
     */

}
