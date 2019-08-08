package cn.hp.hp.hp;


import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Completable;
import rx.Observable;

/**
 * Created by lyy on 2019/6/11.
 */

public interface DataService {
    @GET("/info_min")
    Observable<SimpleDatasBean> getData();

    @GET("get_t1")
    Observable<T1Bean> getT1ByHour(@Query("hour") int hour);

    @GET("get_h1")
    Observable<T1Bean> getH1ByHour(@Query("hour") int hour);

    @GET("get_pressure1")
    Observable<T1Bean> getPressure1ByHour(@Query("hour") int hour);

    @GET("get_rainfall")
    Observable<T1Bean> getRainFallByHour(@Query("hour") int hour);

    @GET("get_a1_last")
    Observable<A1Bean> getA1ByHour();

    @GET("getNewPicture")
    Observable<PictureData> getNewPicture();

    @GET("get_T1")
    Observable<T1Bean> get_T1(@Query("hour") int hour);


    @GET("get_Humidity1_mean")
    Observable<T1Bean> get_Humidity1_mean(@Query("hour") int hour);

    @GET("get_Pressure1_mean")
    Observable<T1Bean> get_Pressure1_mean(@Query("hour") int hour);

    @GET("get_rainfall_mean")
    Observable<T1Bean> get_rainfall_mean(@Query("hour") int hour);

    @GET("get_A1_variance")
    Observable<T1Bean> get_A1_variance(@Query("hour") int hour);

    @GET("get_illuminance_mean")
    Observable<T1Bean> get_illuminance_mean(@Query("hour") int hour);

    @GET("get_V1_mean")
    Observable<T1Bean> get_V1_mean(@Query("hour") int hour);

    @GET("get_displacement1_mean")
    Observable<T1Bean> get_displacement1_mean(@Query("hour") int hour);

}
