package com.example.hmidemo.util;


import android.util.Log;

import com.example.hmidemo.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/11 0011.
 */

public class Utility {
    /**
     * 将返回的json数据解析成weather实体类
     */
    public static Weather handlerWeatherResponse(String response){  //Weather
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray =jsonObject.getJSONArray("results");    //name:"HeWeather"
            String weatherContent=jsonArray.getJSONObject(0).toString();
            Log.i("123","GONGJU"+weatherContent);
//            return new Gson().fromJson(weatherContent,Weather.class);
            return new Gson().fromJson(weatherContent,Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
