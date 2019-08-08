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

}
