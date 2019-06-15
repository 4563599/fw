package com.liyu.fakeweather.hp;


import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by lyy on 2019/6/11.
 */

public interface DataService {
    @GET("/info_min")
    Observable<SimpleDatasBean> getData();
}
