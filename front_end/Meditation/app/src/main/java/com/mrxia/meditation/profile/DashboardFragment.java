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


import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
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

    private LineChartView lineChart;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    String[] data = {"0"};//X轴的标注
    int[] score= {0};//图表的数据
    String[] day = {"03:00","06:00","09:00","12:00","15:00","18:00","21:00","24:00"};
    int[] day_score = {0,9,2,15,3,8,14,0};
    String[] week = {"Mon.","Tue.","Wed.","Thur.","Fri.","Sat.","Sun."};
    int[] week_score = {26,39,28,25,38,18,30};
    String[] month = {"Jan","Feb","Mar.","Apr.","May.","June","July","Aug.","Sept.","Oct."};
    int[] month_score = {143,186,169,128,156,164,159,173,123,201,173,190};
    Button mDay_Button;
    Button mWeek_Button;
    Button mMonth_Button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 1. 加载布局，第三个参数必须为`false`，否则会加载两次布局并且抛出异常！！
        View view = inflater.inflate(R.layout.fragment_profile_dashboard, container, false);
        lineChart = view.findViewById(R.id.chart);
        mDay_Button = view.findViewById(R.id.day_button);
        mWeek_Button = view.findViewById(R.id.week_button);
        mMonth_Button = view.findViewById(R.id.month_button);
        registerListener();
        return view;
    }

    public void registerListener(){
        mDay_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mDay_Button.setActivated(true);
                mWeek_Button.setActivated(false);
                mMonth_Button.setActivated(false);
                mPointValues.clear();
                mAxisXValues.clear();
                setChartData(1);
                getAxisXLables();//获取x轴的标注
                getAxisPoints();//获取坐标点
                initLineChart(1);//初始化


            }
        });
        mWeek_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mDay_Button.setActivated(false);
                mWeek_Button.setActivated(true);
                mMonth_Button.setActivated(false);
                mPointValues.clear();
                mAxisXValues.clear();
                setChartData(2);
                getAxisXLables();//获取x轴的标注
                getAxisPoints();//获取坐标点
                initLineChart(2);//初始化

            }
        });
        mMonth_Button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mDay_Button.setActivated(false);
                mWeek_Button.setActivated(false);
                mMonth_Button.setActivated(true);
                mPointValues.clear();
                mAxisXValues.clear();
                setChartData(3);
                getAxisXLables();//获取x轴的标注
                getAxisPoints();//获取坐标点
                initLineChart(3);//初始化


            }
        });
    }



    /**
     * 初始化LineChart的一些设置
     */
    private void initLineChart(int whichButton){
        Line line;
        line = new Line(mPointValues).setColor(Color.parseColor("#5f6368"));
        if(whichButton == 1) {
            line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色
        }
        else if(whichButton == 2) {
            line = new Line(mPointValues).setColor(Color.parseColor("#8858dc"));  //折线的颜色
        }
        else if(whichButton == 3) {
            line = new Line(mPointValues).setColor(Color.parseColor("#e94335"));  //折线的颜色
        }
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(false);//曲线是否平滑
//	    line.setStrokeWidth(3);//线条的粗细，默认是3
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.parseColor("#5f6368"));//灰色

        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(10); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
//	    data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线


        Axis axisY = new Axis();  //Y轴
        axisY.setName("mins");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setTextColor(Color.parseColor("#5f6368"));//灰色
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        lineChart.setMaxZoom((float) 3);//缩放比例
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
         * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
         * 若设置axisX.setMaxLabelChars(int count)这句话,
         * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
         刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
         若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
         * 并且Y轴是根据数据的大小自动设置Y轴上限
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 9;
        lineChart.setCurrentViewport(v);
    }

    /**
     * X 轴的显示
     */
    private void getAxisXLables(){
        for (int i = 0; i < data.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(data[i]));
        }
    }
    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints(){
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }

    private void setChartData(int whichButton)
    {
        if(whichButton == 1) {
            data = day;
            score = day_score;
        }
        else if(whichButton == 2) {
            data = week;
            score = week_score;
        }
        else if(whichButton == 3) {
            data = month;
            score = month_score;
        }
    }

}
