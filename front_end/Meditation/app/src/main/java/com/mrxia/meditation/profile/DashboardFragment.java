package com.mrxia.meditation.profile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;


import com.mrxia.meditation.R;

public class DashboardFragment extends Fragment {
    public static DashboardFragment newInstance() {
        DashboardFragment frag = new DashboardFragment();
        return frag;
    }

    Button mDay_Button;
    Button mWeek_Button;
    Button mMonth_Button;
    private List<PointValue> values;
    private List<Line> lines;
    private LineChartData lineChartData;
    private LineChartView lineChartView;
    private List<Line> linesList;
    private List<PointValue> pointValueList;
    private List<PointValue> points;
    private int position = 0;
    private Timer timer;
    private boolean isFinish = true;
    private Axis axisY, axisX;
    private Random random = new Random();
    int pointCount = 0;
    int lineColor = 0;//0为红色，1为绿色，2为蓝色

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_profile_dashboard, container, false);
        initView(view);
        registerListener();
        return view;
    }

    public void registerListener(){
        mDay_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mDay_Button.setActivated(true);
                mWeek_Button.setActivated(false);
                mMonth_Button.setActivated(false);
                pointCount = 0;
                lineColor = 0;
                position = 0;
                pointValueList.clear();
                drawChart(7);
                drawChart(7);
            }
        });
        mWeek_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mDay_Button.setActivated(false);
                mWeek_Button.setActivated(true);
                mMonth_Button.setActivated(false);
                pointCount = 0;
                lineColor = 1;
                position = 0;
                pointValueList.clear();
                drawChart(7);
                drawChart(7);
            }
        });
        mMonth_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mDay_Button.setActivated(false);
                mWeek_Button.setActivated(false);
                mMonth_Button.setActivated(true);
                pointCount = 0;
                lineColor = 2;
                position = 0;
                pointValueList.clear();
                drawChart(7);
                drawChart(7);
            }
        });
    }

    public void drawChart(int pointNum){
        PointValue value;
        for(int i=0;i<=pointNum;i++){
            value = new PointValue(position * 1, random.nextInt(100) + 40);
            pointValueList.add(value);
            position++;
        }
        float x = pointValueList.get(pointValueList.size()-1).getX();
        Line line = new Line(pointValueList);
        if(lineColor == 0){
            line.setColor(Color.RED);
        }
        else if(lineColor == 1){
            line.setColor(Color.GREEN);
        }
        else{
            line.setColor(Color.BLUE);
        }
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(true);//曲线是否平滑，即是曲线还是折线

        linesList.clear();
        linesList.add(line);
        lineChartData = initDatas(linesList);
        lineChartView.setLineChartData(lineChartData);
    }


    public void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(pointCount>=9){
                    stopTimer();
                }
                //实时添加新的点
                PointValue value1 = new PointValue(position * 5, random.nextInt(100) + 40);
                pointValueList.add(value1);

                float x = value1.getX();
                //根据新的点的集合画出新的线
                Line line = new Line(pointValueList);
                if(lineColor == 0){
                    line.setColor(Color.RED);
                }
                else if(lineColor == 1){
                    line.setColor(Color.GREEN);
                }
                else{
                    line.setColor(Color.BLUE);
                }
                line.setShape(ValueShape.CIRCLE);
                line.setCubic(true);//曲线是否平滑，即是曲线还是折线

                linesList.clear();
                linesList.add(line);
                lineChartData = initDatas(linesList);
                lineChartView.setLineChartData(lineChartData);

                Viewport port;
                port = initViewPort(0, 50);
                //根据点的横坐实时变幻坐标的视图范围
//                    if (x > 50) {
//                        port = initViewPort(x - 50, x);
//                    } else {
//                        port = initViewPort(0, 50);
//                    }
                lineChartView.setCurrentViewport(port);//当前窗口

                Viewport maPort = initMaxViewPort(x);
                lineChartView.setMaximumViewport(maPort);//最大窗口
                position++;
                pointCount++;
            }
        }, 0, 1);
    }

    public void stopTimer(){
        timer.cancel();
        timer = null;
    }

    public void initView(View view){
        lineChartView = view.findViewById(R.id.chart);
        pointValueList = new ArrayList<>();
        linesList = new ArrayList<>();
        mDay_Button = view.findViewById(R.id.day_button);
        mWeek_Button = view.findViewById(R.id.week_button);
        mMonth_Button = view.findViewById(R.id.month_button);


        //初始化坐标轴
        axisY = new Axis();
        //添加坐标轴的名称
        axisY.setLineColor(Color.parseColor("#aab2bd"));
        axisY.setTextColor(Color.parseColor("#aab2bd"));
        axisX = new Axis();
        axisX.setLineColor(Color.parseColor("#aab2bd"));
        lineChartData = initDatas(null);
        lineChartView.setLineChartData(lineChartData);

        Viewport port = initViewPort(0, 7);
        lineChartView.setCurrentViewportWithAnimation(port);
        lineChartView.setInteractive(false);
        lineChartView.setScrollEnabled(true);
        lineChartView.setValueTouchEnabled(true);
        lineChartView.setFocusableInTouchMode(true);
        lineChartView.setViewportCalculationEnabled(false);
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartView.startDataAnimation();
        lineChartView.setCurrentViewport(port);//当前窗口
        Viewport maPort = initMaxViewPort(7);
        lineChartView.setMaximumViewport(maPort);//最大窗口
        points = new ArrayList<>();
    }

    private LineChartData initDatas(List<Line> lines) {
        LineChartData data = new LineChartData(lines);
        data.setAxisYLeft(axisY);
        data.setAxisXBottom(axisX);
        return data;
    }

    /**
     * 当前显示区域
     *
     * @param left
     * @param right
     * @return
     */
    private Viewport initViewPort(float left, float right) {
        Viewport port = new Viewport();
        port.top = 150;
        port.bottom = 0;
        port.left = left;
        port.right = right;
        return port;
    }

    /**
     * 最大显示区域
     *
     * @param right
     * @return
     */
    private Viewport initMaxViewPort(float right) {
        Viewport port = new Viewport();
        port.top = 150;
        port.bottom = 0;
        port.left = 0;
        port.right = right + 50;
        return port;
    }
}
