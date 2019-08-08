package cn.hp.hp;

import android.app.Application;
import android.content.Context;

import com.hp.hp.BuildConfig;

import org.litepal.LitePal;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by liyu on 2016/11/2.
 */

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        LitePal.initialize(this);
        if (!BuildConfig.DEBUG) {
            AppExceptionHandler.getInstance().setCrashHanler(this);
        }
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static Context getContext() {
        return mContext;
    }

}
