package cn.hp.hp.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.hp.hp.R;

import java.util.List;

import cn.hp.hp.hp.PictureData;
import cn.hp.hp.hp.RetrofitUtil;
import cn.hp.hp.ui.base.BaseActivity;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lyy on 2019/6/18.
 */

public class ImagesActivity extends BaseActivity {

    private TextView mTvContent;
    private PhotoView iv_pic;
    private TextView tv_content;


    @Override
    protected int getLayoutId() {
        return R.layout.act_imgs;
    }

    @Override
    protected int getMenuId() {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        iv_pic = findViewById(R.id.iv_pic);
        tv_content = findView(R.id.tv_content);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        RetrofitUtil.getInstance().getDataService().getNewPicture().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PictureData>() {
            @Override
            public void onCompleted() {
                Log.i("lyy08", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("lyy08", "e :" + e.toString());
            }

            @Override
            public void onNext(PictureData pictureData) {
                tv_content.setText(pictureData.getUpdate_time() + " 获取到最新现场图片");
                Glide.with(ImagesActivity.this).load(pictureData.getPic_ulr()).placeholder(R.drawable.ic_default_image).into(iv_pic);
            }

        });
    }

    @Override
    protected void loadData() {

    }

}
