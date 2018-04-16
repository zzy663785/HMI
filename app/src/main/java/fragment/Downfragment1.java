package fragment;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hmidemo.MainActivity;
import com.example.hmidemo.MyWatch;
import com.example.hmidemo.R;
import com.example.hmidemo.Var;
//import com.example.hmidemo.gson.Weather;
import com.example.hmidemo.util.Connection;
import com.example.hmidemo.util.HttpUtil;
import com.example.hmidemo.util.UIrun;
import com.facebook.shimmer.ShimmerFrameLayout;

/**
 * Created by Administrator on 2017/12/10 0010.
 */

public class Downfragment1 extends Fragment {

    private TextView CarChange;
    private TextView RealSpeeed;
    private TextView AdviceSpeed;
    private TextView FrontGap;
    private TextView BackGap;
    private ImageView imageView;
    private MyWatch wat;
    private int data;
    ShimmerFrameLayout shimmerFrameLayout;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view=inflater.inflate(R.layout.zhuy_down1_fragment,container,false);

        CarChange=view.findViewById(R.id.carchange);
        imageView=view.findViewById(R.id.changeLaneView);
//        RealSpeeed=view.findViewById(R.id.realspeed);
        AdviceSpeed=view.findViewById(R.id.advicespeed);
        FrontGap=view.findViewById(R.id.backgap);
        BackGap=view.findViewById(R.id.frontgap);
        wat = (MyWatch)view.findViewById(R.id.wat);

         shimmerFrameLayout =view.findViewById(R.id.shimmerContent);

        Var.getInstance().setBroadListener(new Var.BroadListener() {
            @Override
            public View onSuccess() {
                changeLane();
                speedMessage();
                return view;
            }
        });
        return view;
    }

    /*
    车辆信息
     */
    public void speedMessage(){
        if (Connection.intarr!=null){
//            UIrun.SetText(RealSpeeed,Connection.intarr[0]+"km/h");
            wat.setSpeed(Connection.intarr[0]);
            UIrun.SetText(AdviceSpeed,Connection.intarr[1]+"km/h");
            UIrun.SetText(FrontGap,Connection.intarr[2]+"m");
            UIrun.SetText(BackGap,Connection.intarr[3]+"m");
        }
    }

    /**
     * 变道信息
     */
    public void changeLane(){

        if (Connection.intarr!=null){
            if (Connection.intarr[3]==0){
                UIrun.SetText(CarChange,"第"+Connection.intarr[4]+"车左转中");
                UIrun.SetImage(imageView,R.drawable.left);
                shimmerFrameLayout.setRepeatMode(ObjectAnimator.REVERSE);
                shimmerFrameLayout.setBaseAlpha((float) 0.1);//设置没有光照的地方的透明度
                shimmerFrameLayout.setIntensity(10);//设置光的强度
                shimmerFrameLayout.startShimmerAnimation();
            }
            if (Connection.intarr[3]==1){
                UIrun.SetText(CarChange,"第"+Connection.intarr[4]+"车右转中");
                UIrun.SetImage(imageView,R.drawable.right);
                shimmerFrameLayout.setBaseAlpha((float) 0.1);//设置没有光照的地方的透明度
                shimmerFrameLayout.setIntensity(10);//设置光的强度
                shimmerFrameLayout.startShimmerAnimation();
            }
            if (Connection.intarr[3]==2){
                UIrun.SetText(CarChange,"车辆编队行驶中");
                UIrun.SetImage(imageView,R.drawable.qianjin);
                shimmerFrameLayout.stopShimmerAnimation();
            }
            if (Connection.intarr[3]==3){
                UIrun.SetText(CarChange,"车辆编队停止行驶");
                UIrun.SetImage(imageView,R.drawable.stop);
                shimmerFrameLayout.setBaseAlpha((float) 0.1);//设置没有光照的地方的透明度
                shimmerFrameLayout.setIntensity(10);//设置光的强度
                shimmerFrameLayout.startShimmerAnimation();
            }
            if (Connection.intarr[3]==4){
                UIrun.SetText(CarChange,"车间距过近");
                UIrun.SetImage(imageView,R.drawable.carwarning);
                shimmerFrameLayout.setBaseAlpha((float) 0.1);//设置没有光照的地方的透明度
                shimmerFrameLayout.setIntensity(10);//设置光的强度
                shimmerFrameLayout.startShimmerAnimation();
            }

        }
    }
}
