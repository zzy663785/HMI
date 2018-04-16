package com.example.hmidemo.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public class NaviGo {

    public void startNaviGao(Context context, LatLng ll) {
        if (isAvilible(context, "com.autonavi.minimap")) {
            try {
                Log.i("postion:",ll.latitude+"    "+ll.longitude);
                //高德地图,起点就是定位点
                // 终点是LatLng ll = new LatLng("你的纬度latitude","你的经度longitude");
                //sourceApplication
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);

                //将功能Scheme以URI的方式传入data
                Uri uri = Uri.parse("androidamap://navi?sourceApplication=appname&poiname=fangheng&lat=" + ll.latitude + "&lon=" + ll.longitude + "&dev=1&style=2");
                intent.setData(uri);
                //启动该页面即可
//                startActivity(intent);
//                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=（随意写）&poiname=我的目的地&lat=" + ll.latitude + "&lon=" + ll.longitude + "&dev=0");
                context.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context,"您尚未安装高德地图或地图版本过低",Toast.LENGTH_SHORT).show();
        }
    }

    //验证各种导航地图是否安装
    public  boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
