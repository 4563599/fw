package cn.hp.hp.http.api;

import cn.hp.hp.http.BaseAppResponse;
import cn.hp.hp.http.BaseHeWeatherCityResponse;
import cn.hp.hp.model.UpdateInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by liyu on 2016/12/1.
 */

public interface AppController {

    @GET("http://api.caoliyu.cn/appupdate.json")
    Observable<BaseAppResponse<UpdateInfo>> checkUpdate();

    @GET("https://raw.githubusercontent.com/li-yu/FakeWeather/master/api/weatherkey.json")
    Observable<BaseAppResponse<String>> getWeatherKey();

    @GET("https://raw.githubusercontent.com/li-yu/FakeWeather/master/api/heweather_city_list.json")
    Observable<BaseHeWeatherCityResponse> getHeWeatherCityList();
}
