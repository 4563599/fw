package com.liyu.fakeweather.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.liyu.fakeweather.R;
import com.liyu.fakeweather.hp.RetrofitUtil;
import com.liyu.fakeweather.hp.SimpleDatasBean;
import com.liyu.fakeweather.ui.base.BaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by lyy on 2019/6/13.
 */

public class Char1Activity extends BaseActivity implements OnProgressBarListener {

    private LineChart chart;
    private Toolbar mToolbar;
    private LineData data;
    private LineDataSet set;
    private int i = 0;
    private int j = 0;
    private ArrayList<Entry> values = new ArrayList<>();
    private SimpleDatasBean mSimpleDatasBean;
    Subscription subscription1, subscription2;
    private TextView tv_now_hum;
    private TextView tv_now_pres;
    private LinearLayout ll_progress;

    private NumberProgressBar bnp;
    private Timer timer;
    private float total = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.chart1_activity;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mToolbar = findView(R.id.toolbar);
        mToolbar.setTitle("温度数据");

        bnp = (NumberProgressBar) findViewById(R.id.numberbar1);
        bnp.setOnProgressBarListener(this);
    }

    @Override
    protected void loadData() {
        chart = findViewById(R.id.chart);
        tv_now_hum = findViewById(R.id.tv_now_hum);
        tv_now_pres = findViewById(R.id.tv_now_pres);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        ll_progress = findViewById(R.id.ll_progress);
        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);


        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(true);
        chart.invalidate();
        initChart();
    }


    private void initChart() {

        YAxis y1 = chart.getAxisLeft();

        subscription1 = RetrofitUtil.getInstance().getDataService()
                .getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        chart.setVisibility(View.GONE);
                        ll_progress.setVisibility(View.VISIBLE);
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        bnp.incrementProgressBy(1);
                                    }
                                });
                            }
                        }, 2000, 500);
                    }
                })
                .subscribe(new Observer<SimpleDatasBean>() {
                    @Override
                    public void onCompleted() {
                        total = 0;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("lyy08", "exception " + e.toString());
                    }

                    @Override
                    public void onNext(SimpleDatasBean simpleDatasBean) {
                        /**
                         * 主线程中把得到的数据更新UI界面
                         */
                        // mTextMessage.setText(new Gson().toJson(repos));

//                        values1.add(new Entry(1, 1));
//                        values1.add(new Entry(2, 2));
//                        values1.add(new Entry(3, 3));
//                        values1.add(new Entry(4, 4));
//                        values1.add(new Entry(5, 5));
                        timer.cancel();
                        bnp.setProgress(90);
                        data = chart.getData();
                        if (data == null) {
                            data = new LineData();
                            chart.setData(data);
                        }

                        ILineDataSet set = data.getDataSetByIndex(0);
                        if (set == null) {
                            set = new LineDataSet(null, "DataSet 1");
                            data.addDataSet(set);
                        }
                        data.addEntry(new Entry(1, 1), 0);
                        data.notifyDataChanged();

                        chart.notifyDataSetChanged();
                        chart.setVisibleXRangeMaximum(6);
                        chart.moveViewTo(data.getEntryCount() - 7, 50f, YAxis.AxisDependency.LEFT);
                        mSimpleDatasBean = simpleDatasBean;
                    }
                });

        subscription2 = Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())//请求在新的线程中执行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("lyy08", "exception " + e.toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (mSimpleDatasBean == null) {
                            return;
                        }
                        if (i >= mSimpleDatasBean.getData().size()) {
                            i = 0;
                        }
                        if (values.size() < 10) {
                            bnp.incrementProgressBy(1);
                            values.add(new Entry(j, Float.parseFloat(mSimpleDatasBean.getData().get(i).getT1())));
                            total += Float.parseFloat(mSimpleDatasBean.getData().get(i).getT1());
                        } else if (values.size() == 10) {
                            total += Float.parseFloat(mSimpleDatasBean.getData().get(i).getT1());
                            bnp.incrementProgressBy(1);
                            values.add(new Entry(j, Float.parseFloat(mSimpleDatasBean.getData().get(i).getT1())));
                            float avg = total / 11;
                            LimitLine ll1 = new LimitLine(avg, "");
                            ll1.setLineWidth(0.5f);
                            ll1.enableDashedLine(0.5f, 0.5f, 0f);
                            ll1.setTextSize(10f);
                            ll1.setLineColor(Color.BLACK);
                            YAxis yAxis;
                            yAxis = chart.getAxisLeft();
                            yAxis.addLimitLine(ll1);
                        } else {
                            chart.setVisibility(View.VISIBLE);
                            ll_progress.setVisibility(View.GONE);
                            values.remove(0);
                            values.add(new Entry(j, Float.parseFloat(mSimpleDatasBean.getData().get(i).getT1())));
                            tv_now_hum.setText(mSimpleDatasBean.getData().get(i).getTime());
                            tv_now_pres.setText(mSimpleDatasBean.getData().get(i).getT1());
                        }

                        set = new LineDataSet(values, "湿度 1");

                        set.setColor(Color.BLACK);
                        set.setLineWidth(1f);
                        set.setDrawValues(true);
                        set.setDrawCircles(false);
                        set.setMode(LineDataSet.Mode.LINEAR);
                        set.setDrawFilled(false);

                        data = new LineData(set);
                        chart.setData(data);
                        YAxis y3 = chart.getAxisLeft();
                        y3.setAxisMinimum(20f);
                        y3.setAxisMaximum(30f);
                        chart.invalidate();
                        i++;
                        j++;
                        Log.i("lyy08", "i:" + i);
                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription1 != null) {
            subscription1.unsubscribe();
        }
        if (subscription2 != null) {
            subscription2.unsubscribe();
        }

        if (timer != null) {
            timer.cancel();
        }
    }


    @Override
    public void onProgressChange(int current, int max) {
        if (current == max) {
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chart, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_1:
                Toast.makeText(this, "我是第一个", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_2:
                Toast.makeText(this, "我是第二个", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_3:
                Toast.makeText(this, "我是第三个", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_4:
                Toast.makeText(this, "我是第四个", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_5:
                Toast.makeText(this, "我是第三个", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_6:
                Toast.makeText(this, "我是第四个", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}

