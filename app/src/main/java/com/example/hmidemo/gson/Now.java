package com.example.hmidemo.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zzy on 2018/3/29 0029.
 */

public class Now {
    @SerializedName("temperature")
    public String temperature;

    @SerializedName("text")
    public String cond;
}
