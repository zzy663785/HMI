package fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hmidemo.MainActivity;
import com.example.hmidemo.R;
import com.example.hmidemo.Var;
import com.example.hmidemo.Var4line;
import com.example.hmidemo.util.Connection;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * 单个车辆 车速 折线图
 * Created by Administrator on 2018/1/30 0030.
 */

public class UpLeftfragment extends Fragment {

    private LineChart mChart;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        final View view=inflater.inflate(R.layout.upl_fragment,container,false);

        mChart=view.findViewById(R.id.line_chart1);
        ChartInit();
        Var4line.getInstance().setBroadListener(new Var4line.BroadListener() {
            @Override
            public View onSuccess() {
                Log.i(TAG,"on 折线");
                addEntry();
//                if (Connection.intarr!=null){
//                    Log.i(TAG,"on success 1");
//                    int[] count=new int[5];
//                    int[] rang=new int[5];
//
//                    for (int i=0;i<5;i++){
//                        for (int j=6;j<10;j++){
//                            count[i]=Connection.intarr[i];
//                            rang[i]=Connection.intarr[j];
//                        }
//                    }
//
//                }
                return view;
            }
        });
           return view;
    }

    void ChartInit(){
        mChart.setNoDataTextDescription("暂无数据");
        mChart.setDescription("这是折线图");

        mChart.setTouchEnabled(true);
        mChart.setScaleEnabled(true);//缩放
        mChart.setPinchZoom(true);

        mChart.setDrawGridBackground(false);

        mChart.setBackgroundColor(0xff0000); // 设置图表的背景颜色

        LineData data = new LineData();

        // 数据显示的颜色
        data.setValueTextColor(Color.WHITE);

        // 先增加一个空的数据，随后往里面动态添加
        mChart.setData(data);

        // 图表的注解(只有当数据集存在时候才生效)
        Legend l = mChart.getLegend();

        // 可以修改图表注解部分的位置
        // l.setPosition(LegendPosition.LEFT_OF_CHART);

        // 线性，也可是圆
        l.setForm(Legend.LegendForm.LINE);

        // 颜色
        l.setTextColor(Color.WHITE);

        /**
         *  x轴坐标
         */
        XAxis x1= mChart.getXAxis();
        x1.setTextColor(Color.WHITE);
        x1.setDrawGridLines(false);
        x1.setAvoidFirstLastClipping(true);

        x1.setSpaceBetweenLabels(5); // 几个x坐标轴之间才绘制
        x1.setEnabled(true); // 如果false，那么x坐标轴将不可见
        x1.setPosition(XAxis.XAxisPosition.BOTTOM);// 将X坐标轴放置在底部，默认是在顶部。

        // 图表左边的y坐标轴线
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);

        // 最大值
//        leftAxis.setAxisMaxValue(90f);

        // 最小值
//        leftAxis.setAxisMinValue(40f);

        // 不一定要从0开始
        leftAxis.setStartAtZero(false);

        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        // 不显示图表的右边y坐标轴线
        rightAxis.setEnabled(false);

    }

    private void addEntry(){
        LineData data=mChart.getData();
        // 每一个LineDataSet代表一条线，每张统计图表可以同时存在若干个统计折线，这些折线像数组一样从0开始下标。
        // 本例只有一个，那么就是第0条折线
        LineDataSet set = data.getDataSetByIndex(0);
        // 如果该统计折线图还没有数据集，则创建一条出来，如果有则跳过此处代码。
        if (set == null) {
            set = createLineDataSet();
            data.addDataSet(set);
        }
// 先添加一个x坐标轴的值
        // 因为是从0开始，data.getXValCount()每次返回的总是全部x坐标轴上总数量，所以不必多此一举的加1
//        data.addXValue(data.getXValCount()+"秒");
        long time=System.currentTimeMillis();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm:ss");
        Date date=new Date(time);
        String t1=timeFormat.format(date);
        data.addXValue(t1);

// 生成随机测试数
        float f = (float) ((Math.random()) * 20 + 50);

        // set.getEntryCount()获得的是所有统计图表上的数据点总量，
        // 如从0开始一样的数组下标，那么不必多次一举的加1
        Entry entry = new Entry(f, set.getEntryCount());

        // 往linedata里面添加点。注意：addentry的第二个参数即代表折线的下标索引。
        // 因为本例只有一个统计折线，那么就是第一个，其下标为0.
        // 如果同一张统计图表中存在若干条统计折线，那么必须分清是针对哪一条（依据下标索引）统计折线添加。
        data.addEntry(entry, 0);

        // 像ListView那样的通知数据更新
        mChart.notifyDataSetChanged();

        // 当前统计图表中最多在x轴坐标线上显示的总量
        mChart.setVisibleXRangeMaximum(5);

        // y坐标轴线最大值
        // mChart.setVisibleYRange(30, AxisDependency.LEFT);

        // 将坐标移动到最新
        // 此代码将刷新图表的绘图
        mChart.moveViewToX(data.getXValCount() - 5);

        // mChart.moveViewTo(data.getXValCount()-7, 55f,
        // AxisDependency.LEFT);

    }

    // 初始化数据集，添加一条统计折线，可以简单的理解是初始化y坐标轴线上点的表征
    private LineDataSet createLineDataSet() {

        LineDataSet set = new LineDataSet(null, "车速变化");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        // 折线的颜色
        set.setColor(ColorTemplate.getHoloBlue());

        set.setCircleColor(Color.WHITE);
        set.setLineWidth(10f);
        set.setCircleSize(5f);
        set.setFillAlpha(128);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.GREEN);
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);
        set.setDrawValues(true);

        set.setValueFormatter(new ValueFormatter(){
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat decimalFormat = new DecimalFormat();
                String s = decimalFormat.format(value) + "km/h";
                return s;
            }
        });
        return set;
    }
    }
