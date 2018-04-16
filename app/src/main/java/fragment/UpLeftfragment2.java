package fragment;

import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hmidemo.R;
import com.example.hmidemo.Var;
import com.example.hmidemo.Var3bar;
import com.example.hmidemo.util.Connection;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * 所有车辆车速 柱状图
 * Created by Administrator on 2017/11/29 0029.
 */

public class UpLeftfragment2 extends Fragment {

    private static UpLeftfragment2 upLeftfragment2;
    public UpLeftfragment2(){
        upLeftfragment2=this;
    }
    public static UpLeftfragment2 getDownfragment2(){
        return upLeftfragment2;
    }
    private BarChart mBarChart;//显示的图表

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){

        }else {
            Log.i(TAG,"不可见");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        final View view =inflater.inflate(R.layout.upl_fragment2,container,false);

        mBarChart=view.findViewById(R.id.bar_chart);
        mBarChart.setData(new BarData());//设置一个
        mBarChart.invalidate();//空的表
        setBarChartStyle(mBarChart);

        if (Connection.intarr!=null){
            setData(mBarChart, 10);
        }


        Var3bar.getInstance().setBroadListener(new Var3bar.BroadListener() {
            @Override
            public View onSuccess() {
                Log.i(TAG,"on success 2");
                if (Connection.intarr!=null){
                    setBarChartStyle(mBarChart);
                    setData(mBarChart, 10);

                } Log.i(TAG,"up data bar ui");
                return view;

            }


//        if (Connection.CONNECTION_STATE=true) {
//           for (int i = 0; i < data.length; i++) {
//               entries.add(new BarEntry(data[i], i));
//               labels.add(i + "");
//           }
//        entries.add(new BarEntry(data[7], 7));
//        entries.add(new BarEntry(data[8], 8));

//        entries1.add(new BarEntry(4f, 0));
//        entries1.add(new BarEntry(8f, 1));
//        entries1.add(new BarEntry(6f, 2));
//        entries1.add(new BarEntry(12f, 3));

//        dataSet1 = new BarDataSet(entries1, "第二组数据"); //设置数据组的数据
//        //设置数据组的样式（参数是显示颜色的数组）
//        dataSet1.setColors(ColorTemplate.PASTEL_COLORS);
//        //设置柱状图上方显示数据字体大小
//        dataSet1.setValueTextSize(14);
//        labels.add("10");
//           /**
//            * 表数据设置
//            */
//           dataSet = new BarDataSet(entries, "第一组数据");
//           dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//           dataSet.setValueTextSize(14);
//
//           ArrayList<BarDataSet> dataSets = new ArrayList<>();
//           dataSets.add(dataSet);
////        dataSets.add(dataSet1);
//           BarData data = new BarData(labels, dataSets);
//           barChart.setData(data);


            });
        return view;
    }

    public void setBarChartStyle(BarChart mBarChart){

        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);
        mBarChart.setDescription("zzy测试数据");
//        mBarChart.setMaxVisibleValueCount(60);//设置最大可见绘制的 chart count 的数量。 只在 setDrawValues() 设置为 true 时有效。
        mBarChart.setPinchZoom(false);//缩放
        mBarChart.setDrawGridBackground(false);
        mBarChart.setTouchEnabled(true);
        mBarChart.setScaleEnabled(true);
        mBarChart.setDragEnabled(true);
        /*
滑动效果
 */
        Matrix matrix=new Matrix();
        matrix.postScale(1.5f,1);
        mBarChart.getViewPortHandler().refresh(matrix,mBarChart,false);
        mBarChart.animateX(1000);
        mBarChart.setVisibleXRangeMaximum(5);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setTextColor(Color.WHITE);

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setLabelCount(5, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setTextColor(Color.WHITE);

        Legend mLegend = mBarChart.getLegend();
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        mLegend.setForm(Legend.LegendForm.SQUARE);
        mLegend.setFormSize(15f);
        mLegend.setTextSize(12f);
        mLegend.setTextColor(Color.WHITE);
        mLegend.setXEntrySpace(5f);

    }

    public void setData(BarChart mBarChart, int count) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(i, i + "");
        }

        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            int val=Connection.intarr[i];
            yVals.add(new BarEntry(val, i));
        }

        BarDataSet mBarDataSet = new BarDataSet(yVals, "车辆速度");

        // 如果是0f，那么柱状图之间将紧密无空隙的拼接在一起形成一片。
        mBarDataSet.setBarSpacePercent(15f);

        // 柱状图柱的颜色
        mBarDataSet.setColor(Color.rgb(30,144,255));

        // 当柱状图某一柱被选中时候的颜色
        mBarDataSet.setHighLightColor(Color.WHITE);
        mBarDataSet.setValueTextColor(Color.WHITE);

        mBarDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat decimalFormat = new DecimalFormat();
                String s = decimalFormat.format(value) + "km/h";
                return s;
            }
        });
        mBarChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Toast.makeText(getContext(),e.getVal()+"",Toast.LENGTH_SHORT).show();
//                TextView speed=getActivity().findViewById(R.id.realspeed);
//                speed.setText(e.getVal()+"km/h");
            }

            @Override
            public void onNothingSelected() {

            }
        });

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(mBarDataSet);

        BarData mBarData = new BarData(xVals, dataSets);
        mBarData.setValueTextSize(10f);
        mBarChart.setData(mBarData);
    }


}
