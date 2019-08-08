package cn.hp.hp.ui;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.hp.hp.R;
import com.mingle.widget.LoadingView;
import com.moos.library.HorizontalProgressView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import cn.hp.hp.hp.RetrofitUtil;
import cn.hp.hp.hp.T1Bean;
import cn.hp.hp.ui.view.DynamicTitleBar;
import cn.hp.hp.ui.view.MyMarkerView;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyy on 2019/3/14.
 */

public class CharPressure1Activity extends Activity implements OnChartValueSelectedListener, DynamicTitleBar.OnClickTitleBarListener {
    private LoadingView loadView;
    private LineChart chart;
    private TextView tvX, tvY;
    private TextView tv_time;
    //设置数据源
    private List<String> mColorData = new ArrayList<>();
    private DynamicTitleBar dynamic_title;
    private TextView chart_consumer_time;
    private TextView chart_tv_1, chart_tv_2, chart_tv_3, chart_tv_4;
    private HorizontalProgressView horizontalProgressView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chartt3_activity);
        loadView = findViewById(R.id.loadView);
        dynamic_title = findViewById(R.id.dynamic_title);
        chart_consumer_time = findViewById(R.id.chart_consumer_time);

        horizontalProgressView = (HorizontalProgressView) findViewById(R.id.progressView_horizontal);

        chart_tv_1 = findViewById(R.id.chart_tv_1);
        chart_tv_2 = findViewById(R.id.chart_tv_2);
        chart_tv_3 = findViewById(R.id.chart_tv_3);
        chart_tv_4 = findViewById(R.id.chart_tv_4);
        List<String> titles = new ArrayList<>();
        titles.add("最近1小时");
        titles.add("最近6小时");
        titles.add("最近12小时");
        titles.add("最近24小时");
        dynamic_title.setTitleBars(titles);
        dynamic_title.setSelectItem(0);
        dynamic_title.setOnClickTitleBarListener(this);
        getDataFromNet(1);

        int max = 19;
        int min = 6;
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        horizontalProgressView.setProgress(s);
    }

    private void initChart(T1Bean t1Bean) {
        String begin_time = t1Bean.getBaseDataList().get(0).getTime();
        String end_time = t1Bean.getBaseDataList().get(t1Bean.getBaseDataList().size() - 1).getTime();
        chart_consumer_time.setText("获取最新时间为:" + begin_time + "  " + end_time);

        {   // // Chart Style // //

            chart = findViewById(R.id.chart1);

            chart.setVisibility(View.VISIBLE);

            chart.clear();

            chart.destroyDrawingCache();

            // background color
            chart.setBackgroundColor(Color.WHITE);

            // disable description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setTouchEnabled(true);

            // set listeners
            chart.setOnChartValueSelectedListener(this);
            chart.setDrawGridBackground(false);

            // create marker to display box when values are selected
            MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

            // Set the marker to the chart
            mv.setChartView(chart);
            chart.setMarker(mv);

            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart.setPinchZoom(true);
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        float max = Float.parseFloat(t1Bean.getBaseNumData().getMax());
        float min = Float.parseFloat(t1Bean.getBaseNumData().getMin());

        float max_t = Float.parseFloat(t1Bean.getBaseNumData().getMax());
        float min_t = Float.parseFloat(t1Bean.getBaseNumData().getMin());
        float avg = Float.parseFloat(t1Bean.getBaseNumData().getAvg());


        chart_tv_1.setText(t1Bean.getBaseDataList().get(t1Bean.getBaseDataList().size() - 1).getNum());
        chart_tv_2.setText(max_t + "");
        chart_tv_3.setText(min_t + "");
        chart_tv_4.setText(avg + "");
        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(max);
            yAxis.setAxisMinimum(min);
        }


        {   // // Create Limit Lines // //
            LimitLine llXAxis = new LimitLine(avg, "Avg Temperature");
            llXAxis.setLineWidth(2f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);

            LimitLine ll1 = new LimitLine(max_t, "Max Temperature");
            ll1.setLineWidth(2f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);

            LimitLine ll2 = new LimitLine(min_t, "Min Temperature");
            ll2.setLineWidth(2f);
            ll2.enableDashedLine(10f, 10f, 0f);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            ll2.setTextSize(10f);

            // draw limit lines behind data instead of on top
            yAxis.setDrawLimitLinesBehindData(true);
            xAxis.setDrawLimitLinesBehindData(true);

            // add limit lines
            yAxis.getLimitLines().clear();
            yAxis.addLimitLine(ll1);
            yAxis.addLimitLine(ll2);
            //xAxis.addLimitLine(llXAxis);
        }

        setData(t1Bean);

        // draw points over time
        chart.animateX(1000);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);
    }

    private void setData(T1Bean t1Bean) {
        final ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < t1Bean.getBaseDataList().size(); i++) {
            String now_time = t1Bean.getBaseDataList().get(i).getTime();
            String time1 = now_time.substring(now_time.length() - 6, now_time.length() - 4);
            String time2 = now_time.substring(now_time.length() - 4, now_time.length() - 2);
            String time = time1 + ":" + time2;
            Log.i("lyy08", time);
            values.add(new Entry(i, Float.parseFloat(t1Bean.getBaseDataList().get(i).getNum()), time));
        }

        LineDataSet set1;

        XAxis x = chart.getXAxis();


        x.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if ((int) value <= values.size()) {
                    return values.get((int) value).getData() + "";
                } else {
                    return "";
                }

            }
        });

        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setTextSize(12f);


        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            set1.setDrawIcons(false);

            // draw dashed line
//            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(1.5f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(int pos) {
        int hour = 1;
        if (pos == 3) {
            hour = 24;
        } else if (pos == 2) {
            hour = 12;
        } else if (pos == 1) {
            hour = 6;
        } else {
            hour = 1;
        }
        getDataFromNet(hour);
    }

    private void getDataFromNet(int hour) {
        RetrofitUtil.getInstance().getDataService().getPressure1ByHour(hour).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<T1Bean>() {
            @Override
            public void onCompleted() {
                Log.i("lyy08", "onCompleted");
                loadView.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                Log.i("lyy08", e.toString());
                loadView.setVisibility(View.GONE);
            }

            @Override
            public void onNext(T1Bean t1Bean) {
                loadView.setVisibility(View.GONE);
                initChart(t1Bean);
            }

        });
    }
}
