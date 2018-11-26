package github.runnlin.io.mygank.http

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * @author Runnlin
 * @date 2018/11/21/0021.
 */
interface RxGankService {
    /**
     * get Android's data
     */
    @GET("Android/20/{page}")
    fun getAndroidData(@Path("page") page: Int): Observable<GankResultBean>

    /**
     * get Fuli's data
     */
    @GET("福利/20/{page}")
    fun getFuliData(@Path("page") page: Int): Observable<GankResultBean>
}